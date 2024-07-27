package com.krs.knowledgerevisingsystem.controller;

import com.krs.knowledgerevisingsystem.entity.User;
import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import com.krs.knowledgerevisingsystem.service.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class ExcelUploadController {
    private CourseService courseService;
    private SubjectManagerService subjectManagerService;
    private UserService userService;
    private SubjectService subjectService;
    private EnrollmentService enrollmentService;
    private LessonService lessonService;
    private RoleService roleService;

    public ExcelUploadController(SubjectManagerService subjectManagerService, RoleService roleService, LessonService lessonService, UserService userService, SubjectService subjectService, CourseService courseService, EnrollmentService enrollmentService) {
        this.subjectService = subjectService;
        this.subjectManagerService = subjectManagerService;
        this.courseService = courseService;
        this.lessonService = lessonService;
        this.enrollmentService = enrollmentService;
        this.roleService = roleService;
        this.userService = userService;
    }

//    @GetMapping("/uploadExcel")
//    public String uploadExcel() {
//        return "upload-excel"; // Assuming "upload-excel.html" is your upload form view
//    }
//
//    @PostMapping("/uploadExcel")
//    public String uploadExcel(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
//        if (!isValidExcelFile(file)) {
//            redirectAttributes.addFlashAttribute("error", "Please upload a valid Excel file.");
//            return "redirect:/uploadExcel"; // Redirect back to the upload form
//        }
//
//        try (InputStream inputStream = file.getInputStream()) {
//            List<User> users = getUserDataFromExcel(inputStream);
//            for(User u : users){
//
//                userService.save(u.getFullName(), u.getPassword(), u.getEmail(), "STUDENT");
//
//            }
//            redirectAttributes.addFlashAttribute("success", "File uploaded and data saved successfully.");
//        } catch (IOException e) {
//            redirectAttributes.addFlashAttribute("error", "An error occurred while processing the file.");
//            e.printStackTrace(); // Consider logging the exception instead of printing stack trace
//        } catch (SaveAccountInvalidException e) {
//            throw new RuntimeException(e);
//        }
//
//        return "redirect:/home"; // Redirect to home page or any other appropriate page
//    }
//
//    public static boolean isValidExcelFile(MultipartFile file) {
//        return file != null && file.getContentType() != null &&
//                file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//    }
//
//    public static List<User> getUserDataFromExcel(InputStream inputStream) {
//        List<User> users = new ArrayList<>();
//        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
//            Sheet sheet = workbook.getSheetAt(0);
//            for (Row row : sheet) {
//                if (row.getRowNum() == 0) { // Skip the header row
//                    continue;
//                }
//                User user = new User();
//                for (Cell cell : row) {
//                    switch (cell.getColumnIndex()) {
//                        case 0:
//                            user.setFullName(cell.getStringCellValue());
//                            break;
//                        case 1:
//                            user.setEmail(cell.getStringCellValue());
//                            break;
//                        // Add more cases if there are more columns
//                        default:
//                            break;
//                    }
//                }
//                // Generate a random password for each user
//                String randomPassword = generateRandomPassword(6);
//                user.setPassword("1");
//                user.setStatus(true);
//                users.add(user);
//            }
//        } catch (IOException e) {
//            e.printStackTrace(); // Consider logging the exception instead of printing stack trace
//        }
//        return users;
//    }
//
//    public static String generateRandomPassword(int length) {
//        // Define the character set from which to generate the password
//        String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
//
//        // Initialize the random number generator
//        Random random = new Random();
//
//        // Initialize a StringBuilder to store the generated password
//        StringBuilder password = new StringBuilder();
//
//        // Generate random characters
//        for (int i = 0; i < length; i++) {
//            // Generate a random index within the charset length
//            int randomIndex = random.nextInt(charset.length());
//
//            // Append the character at the random index to the password
//            password.append(charset.charAt(randomIndex));
//        }
//
//        return password.toString();
//    }
//
//    public boolean validateUser(User user) {
//        boolean valid = false;
//        if(userService.findByUser(user.getEmail()) == null){
//            valid = true;
//        }
//        return valid;
//    }
}

