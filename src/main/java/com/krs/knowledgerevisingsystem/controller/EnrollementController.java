package com.krs.knowledgerevisingsystem.controller;

import com.krs.knowledgerevisingsystem.entity.Course;
import com.krs.knowledgerevisingsystem.entity.Enrollment;
import com.krs.knowledgerevisingsystem.entity.Role;
import com.krs.knowledgerevisingsystem.entity.User;
import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import com.krs.knowledgerevisingsystem.service.*;
import jakarta.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class EnrollementController {
    private CourseService courseService;
    private UserService userService;
    private EnrollmentService enrollmentService;

    public EnrollementController(LessonService lessonService, UserService userService, SubjectService subjectService, CourseService courseService, EnrollmentService enrollmentService) {
        this.userService = userService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("course/detail/participant/{courseId}")
    public ModelAndView participant(Principal principal, @PathVariable Long courseId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "3") int size) {
        ModelAndView modelAndView = new ModelAndView();
        Pageable pageable = PageRequest.of(page, size);
        Course course = courseService.findById(courseId).get();
        Page<Enrollment> participantList = enrollmentService.findAllByCourseId(course.getId(),pageable);
        modelAndView.setViewName("participant");
        List<Role> list = userService.getAllRole();
        list = list.stream()
                .filter(role -> role.getSpeciality().equals("User Role"))
                .collect(Collectors.toList());
        modelAndView.addObject("roleList",list);
        modelAndView.addObject("participantList", participantList.getContent());
        modelAndView.addObject("course", course);
        modelAndView.addObject("user", userService.findByUser(principal.getName()));

        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", participantList.getTotalPages());
        return modelAndView;
    }

    @RequestMapping("course/detail/participant/{courseId}/search")
    public ModelAndView search( @RequestParam(value = "roleName", required = false) String roleName,HttpSession session,@RequestParam(value = "name", required = false) String name,@RequestParam(value = "role", required = false) String role,@RequestParam(value = "status", required = false) String status, @PathVariable Long courseId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "9") int size) {
        ModelAndView modelAndView = new ModelAndView();
        Pageable pageable = PageRequest.of(page, size);
        Course course = courseService.findById(courseId).get();
        Page<Enrollment> participantList = null;
        session.setAttribute("name", name);
        if (name != null && (role == null || role.isEmpty()) && (status == null || status.isEmpty())) {
            participantList = enrollmentService.findAllByCourseIdAndUser_FullNameContaining(courseId, name, pageable);
        } else if (name != null && role != null && !role.isEmpty() && (status == null || status.isEmpty())) {
            participantList = enrollmentService.findAllByCourseIdAndUser_FullNameContainingAndUser_Role_Name(courseId, name, role, pageable);
        } else if (name != null && (role == null || role.isEmpty()) && status != null && !status.isEmpty()) {
            boolean status2 = Boolean.parseBoolean(status);
            participantList = enrollmentService.findAllByCourseIdAndUser_FullNameContainingAndUser_Status(courseId, name, status2, pageable);
        } else if (name != null && role != null && !role.isEmpty() && status != null && !status.isEmpty()) {
            boolean status2 = Boolean.parseBoolean(status);
            participantList = enrollmentService.findAllByCourseIdAndUser_FullNameContainingAndUser_Role_NameAndUser_Status(courseId, name, role, status2, pageable);
        } else {
            participantList = enrollmentService.findAllByCourseId(courseId, pageable);
        }
        List<Role> list = userService.getAllRole();
        list = list.stream()
                .filter(role2 -> role2.getSpeciality().equals("User Role"))
                .collect(Collectors.toList());
        modelAndView.setViewName("participant");
        modelAndView.addObject("roleList",list);
        modelAndView.addObject("participantList", participantList.getContent());
        modelAndView.addObject("course", course);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", participantList.getTotalPages());
        return modelAndView;
    }



    @GetMapping("/uploadExcel")
    public String uploadExcel() {
        return "upload-excel"; // Assuming "upload-excel.html" is your upload form view
    }

    @PostMapping("/uploadExcel/course/{courseId}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> uploadExcel(@RequestParam("file") MultipartFile file, @PathVariable Long courseId) {
        Map<String, String> response = new HashMap<>();

        if (!isValidExcelFile(file)) {
            response.put("error", "Please upload a valid Excel file.");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(response);
        }

        try (InputStream inputStream = file.getInputStream()) {
            List<User> users = getUserDataFromExcel(inputStream);

//            List<User> savedUsers = userService.saveAll2(users);
            Course course = courseService.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

            for (User u : users) {
                if (validateUser(u)) {
                    userService.createUser(u.getFullName(),  "1",u.getEmail());
                    Enrollment enrollment = new Enrollment();
                    enrollment.setCourse(course);
                    enrollment.setUser(userService.findByUser(u.getEmail()));
                    enrollmentService. add(enrollment);
                }
            }

            response.put("success", "File uploaded successfully.");
            response.put("redirect", "/course/detail/participant/" + courseId);
        } catch (IOException e) {
            e.printStackTrace();
            response.put("error", "File upload failed due to an IOException.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(response);
        }
//        catch (SaveAccountInvalidException e) {
//            e.printStackTrace();
//            response.put("error", "File upload failed due to a SaveAccountInvalidException.");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(response);
//        }

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }


    public static boolean isValidExcelFile(MultipartFile file) {
        return file != null && file.getContentType() != null &&
                file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public static List<User> getUserDataFromExcel(InputStream inputStream) {
        List<User> users = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Skip the header row
                    continue;
                }
                User user = new User();
                for (Cell cell : row) {
                    switch (cell.getColumnIndex()) {
                        case 0:
                            user.setFullName(cell.getStringCellValue());
                            break;
                        case 1:
                            user.setEmail(cell.getStringCellValue());
                            break;
                        // Add more cases if there are more columns
                        default:
                            break;
                    }
                }
                // Generate a random password for each user
                user.setStatus(true);
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Consider logging the exception instead of printing stack trace
        }
        return users;
    }

    public boolean validateUser(User user) {
        boolean valid = false;
        if(userService.findByUser(user.getEmail()) == null){
            valid = true;
        }
        return valid;
    }



}
