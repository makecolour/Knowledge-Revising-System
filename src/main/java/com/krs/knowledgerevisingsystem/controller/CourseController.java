package com.krs.knowledgerevisingsystem.controller;

import com.krs.knowledgerevisingsystem.entity.*;
import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import com.krs.knowledgerevisingsystem.service.*;
import com.mysql.cj.result.Row;
import jakarta.mail.Multipart;
import jakarta.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;
    private final SubjectService subjectService;
    private final EnrollmentService enrollmentService;
    private final LessonService lessonService;
    private final RoleService roleService;
    private final ExamService examService;

    public CourseController(RoleService roleService,
                            LessonService lessonService,
                            UserService userService,
                            SubjectService subjectService,
                            CourseService courseService,
                            EnrollmentService enrollmentService,
                            ExamService examService) {
        this.subjectService = subjectService;
        this.userService = userService;
        this.courseService = courseService;
        this.lessonService = lessonService;
        this.enrollmentService = enrollmentService;
        this.roleService = roleService;
        this.examService = examService;
    }

    @RequestMapping("/course/detail/{id}")
    public ModelAndView courseDetail(HttpSession session, @RequestParam(value = "name", required = false, defaultValue = "") String name, @PathVariable Long id, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "9") int size) {
        ModelAndView modelAndView = new ModelAndView();
        Course course = courseService.findById(id).get();
        User user = userService.findByUser(principal.getName());
        Pageable pageable = PageRequest.of(page, size);
        Page examList = null;
        session.setAttribute("name", name);
        examList = examService.findAllByCourseIdAndStatusAndNameContaining(id, true, name, pageable);
        List<Enrollment> enrollmentList = enrollmentService.getAllUserSameCourse(course.getId());
        List<Lesson> lessonList = lessonService.getAllLessonSameSubject(course.getSubject().getId());
        //Collections.sort(lessonList, Comparator.comparing(Lesson::getOrder));
        modelAndView.setViewName("course-detail");
        modelAndView.addObject("user", user);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", examList.getTotalPages());
        modelAndView.addObject("course", course);
        modelAndView.addObject("enrollmentList", enrollmentList);
        modelAndView.addObject("examList", examList.getContent());
        modelAndView.addObject("lessonList", lessonList);
        modelAndView.addObject("enroll", enrollmentService.checkEnrollment(user, course));
        return modelAndView;
    }


    @GetMapping("/course")
    public ModelAndView course(HttpSession session, @RequestParam(value = "courseSearch", required = false) String courseSearch, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "9") int size) {

        ModelAndView modelAndView = new ModelAndView();
        if (principal == null) {
            return new ModelAndView("redirect:/login");
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Course> courseList = null;
        session.setAttribute("courseSearch", courseSearch);
        courseList = courseService.findAllByStatus(pageable, true);
        Set<Subject> courseSubjectSet = new HashSet<>();
        for (Course course : courseService.getAllCourse()) {
            courseSubjectSet.add(course.getSubject());
        }
        courseSubjectSet.retainAll(new HashSet<>(subjectService.getAllSubjects()));

        modelAndView.setViewName("course");
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", courseList.getTotalPages());
        modelAndView.addObject("courseList", courseList.getContent());
        modelAndView.addObject("subjectSet", courseSubjectSet);
        modelAndView.addObject("user", userService.findByUser(principal.getName()));
        return modelAndView;
    }

    @RequestMapping("/course/search")
    public ModelAndView searchCourseSubjectName(HttpSession session, @RequestParam(value = "column", required = false) String column, @RequestParam(value = "order", required = false) String direction, @RequestParam(value = "teacher", required = false) Long teacher, @RequestParam(value = "courseSearch", required = false) String courseSearch, @RequestParam(value = "subjectId", required = false) Long subjectId, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "9") int size) {
        ModelAndView modelAndView = new ModelAndView();
        Pageable pageable = PageRequest.of(page, size);
        session.setAttribute("courseSearch", courseSearch);
        Page<Course> courseList = null;
        if (courseSearch != null && (subjectId == null || subjectId.describeConstable().isEmpty())) {
            courseList = courseService.findByCourseNameContaining(pageable, courseSearch, column, direction);
        } else if (courseSearch != null && subjectId != null) {
            courseList = courseService.findAllBySubject_IdAndCourseNameContaining(pageable, subjectId, courseSearch, column, direction);
        } else {
            courseList = courseService.findAllByStatus(pageable, true);
        }

//        List<Course> filteredCourses = courseList.stream()
//                .filter(course -> Objects.equals(course.isStatus(), true))
//                .collect(Collectors.toList());
//        courseList = new PageImpl<>(filteredCourses, pageable, filteredCourses.size());

        Set<Subject> courseSubjectSet = new HashSet<>();
        Set<User> teacherSet = new HashSet<>();

        for (Course course : courseService.getAllCourse()) {
            courseSubjectSet.add(course.getSubject());
            teacherSet.add(course.getTeacher());
        }

        courseSubjectSet.retainAll(new HashSet<>(subjectService.getAllSubjects()));
        teacherSet.retainAll(new HashSet<>(userService.getAllUsers()));
        courseSubjectSet.retainAll(subjectService.getAllSubjects());
        modelAndView.setViewName("course");
        modelAndView.addObject("teacherSet", teacherSet);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", courseList.getTotalPages());
        modelAndView.addObject("courseList", courseList.getContent());
        modelAndView.addObject("subjectSet", courseSubjectSet);
        modelAndView.addObject("user", userService.findByUser(principal.getName()));
        return modelAndView;
    }

    @GetMapping("/course-adding")
    public ModelAndView courseAdding(Principal principal, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
            if ( !user.getRole().getName().equals("ADMIN") && !user.getRole().getName().equals("MANAGER") && !user.getRole().getName().equals("TEACHER")) {
                session.setAttribute("error", "You do not have permission to access this page");
                modelAndView.setViewName("redirect:/dashboard");
                return modelAndView;
            }
        } else {
            return new ModelAndView("redirect:/login");
        }
        List<Subject> subjectList = subjectService.getAllSubjects();
        modelAndView.setViewName("course-adding");
        modelAndView.addObject("subjectList", subjectList);
        List<User> teacherList = userService.getAllUsers();
        teacherList.removeIf(user -> !user.getRole().getName().contains("TEACHER") && !user.getRole().getName().contains("MANAGER") || !user.isStatus());
        teacherList.remove(userService.findByUser(principal.getName()));
        List<Role> semesterList = roleService.findAll();
        semesterList.removeIf(role -> !role.getSpeciality().contains("Semester") || !role.isStatus());
        modelAndView.addObject("teacherList", teacherList);
        modelAndView.addObject("semesterList", semesterList);
        modelAndView.addObject("thisManager", userService.findByUser(principal.getName()));
        return modelAndView;
    }

    @PostMapping("/course-adding")
    public String courseAdding(@RequestParam Long semester, HttpSession session, Model model, @RequestParam boolean status, @RequestParam Long teacher, @RequestParam String courseName, @RequestParam Long subjectId, @RequestParam String description, Principal principal) {
        boolean hasError = false;

        if (courseName == null || courseName.isEmpty()) {
            model.addAttribute("errorName", "Name cannot be null");
            hasError = true;
        } else if (courseName.length() > 20) {
            model.addAttribute("errorName", "Name cannot exceed 20 characters");
            hasError = true;
        }
        if (description == null) {
            model.addAttribute("errorDescription", "Description cannot be null");
            hasError = true;
        }

        for (Course course : courseService.findAllByRole_Id(semester)) {
            if (course.getCourseName().equals(courseName)) {
                model.addAttribute("errorName", "Name already exists in this semester");
                hasError = true;
            }
        }
        if (hasError) {
            List<Subject> subjectList = subjectService.getAllSubjects();
            model.addAttribute("subjectList", subjectList);
            List<User> teacherList = userService.getAllUsers();
            teacherList.removeIf(user -> !user.getRole().getName().contains("TEACHER") && !user.getRole().getName().contains("MANAGER") || !user.isStatus());
            teacherList.remove(userService.findByUser(principal.getName()));
            model.addAttribute("teacherList", teacherList);
            model.addAttribute("thisManager", userService.findByUser(principal.getName()));
            List<Role> semesterList = roleService.findAll();
            semesterList.removeIf(role -> !role.getSpeciality().contains("Semester") || !role.isStatus());
            model.addAttribute("semesterList", semesterList);
            return "course-adding";
        }
        User user = userService.findByUser(userService.findById(teacher).getEmail());
        Subject subject = subjectService.getSubjectById(subjectId);
        Role role = roleService.getRoleById(semester).get();
        try {
            courseService.save(role, courseName.trim(), user, subject, description.trim(), status);
        } catch (SaveAccountInvalidException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/course-admin";
    }

    @RequestMapping("/course/detail/{courseId}/enrollment")
    public String enrollCourse(Model model, Principal principal, @PathVariable Long courseId) {
        User user = userService.findByUser(principal.getName());
        Course course = courseService.findById(courseId).get();
        if (!enrollmentService.checkEnrollment(user, course)) {
            try {
                enrollmentService.save(user, course);
            } catch (SaveAccountInvalidException e) {
                model.addAttribute("errorMessage", e.getMessage());
            }
        } else {
            enrollmentService.deleteEnrollment(user, course);
        }
        return "redirect:/course/detail/" + courseId;
    }


    @GetMapping("/my-course")
    public ModelAndView myCourse(HttpSession session, @RequestParam(value = "courseSearch", required = false) String courseSearch, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "3") int size) {
        ModelAndView modelAndView = new ModelAndView();
        if(principal == null){
            return new ModelAndView("redirect:/login");
        }
        Pageable pageable = PageRequest.of(page, size);
        User user = userService.findByUser(principal.getName());
        session.setAttribute("courseSearch", courseSearch);
        Page<Enrollment> myCourseList;
        if (courseSearch != null) {
            myCourseList = enrollmentService.findAllByCourse_CourseNameContainingAndUserIdAndCourse_Status(pageable, courseSearch, user.getId(), true);
        } else {
            myCourseList = enrollmentService.findAllByUserIdAndCourse_Status(pageable, user.getId(), true);
        }
        Set<Subject> courseSubjectSet = new HashSet<>();
        for (Enrollment e : enrollmentService.findAllByUserId(user.getId())) {
            courseSubjectSet.add(e.getCourse().getSubject());
        }
        courseSubjectSet.retainAll(new HashSet<>(subjectService.getAllSubjects()));
        modelAndView.setViewName("my-course");
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", myCourseList.getTotalPages());
        modelAndView.addObject("myCourseList", myCourseList.getContent());
        modelAndView.addObject("subjectSet", courseSubjectSet);
        modelAndView.addObject("user", userService.findByUser(principal.getName()));
        return modelAndView;
    }

    @RequestMapping("/my-course/search")
    public ModelAndView searchMyCourseSubjectName(HttpSession session, @RequestParam(value = "courseSearch", required = false) String courseSearch, @RequestParam(value = "subjectId", required = false) Long subjectId, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "3") int size) {
        ModelAndView modelAndView = new ModelAndView();
        session.setAttribute("courseSearch", courseSearch);
        Pageable pageable = PageRequest.of(page, size);
        User user = userService.findByUser(principal.getName());
        Page<Enrollment> myCourseList = null;
        if (courseSearch != null && (subjectId == null || subjectId.describeConstable().isEmpty())) {
            myCourseList = enrollmentService.findAllByCourse_CourseNameContainingAndUserIdAndCourse_Status(pageable, courseSearch, user.getId(), true);
        } else if (courseSearch != null && subjectId != null) {
            myCourseList = enrollmentService.findAllByUserIdAndCourse_CourseNameContainingAndCourse_Subject_Id(pageable, user.getId(), courseSearch, subjectId);
        } else {
            myCourseList = enrollmentService.findAllByUserIdAndCourse_Status(pageable, user.getId(), true);
        }
        Set<Subject> courseSubjectSet = new HashSet<>();
        Set<User> teacherSet = new HashSet<>();

        for (Course course : courseService.getAllCourse()) {
            courseSubjectSet.add(course.getSubject());
            teacherSet.add(course.getTeacher());
        }
        courseSubjectSet.retainAll(new HashSet<>(subjectService.getAllByStatus(true)));
        teacherSet.retainAll(new HashSet<>(userService.getAllUsers()));
        modelAndView.setViewName("my-course");
        modelAndView.addObject("teacherSet", teacherSet);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", myCourseList.getTotalPages());
        modelAndView.addObject("myCourseList", myCourseList.getContent());
        modelAndView.addObject("subjectSet", courseSubjectSet);
        modelAndView.addObject("user", userService.findByUser(principal.getName()));
        return modelAndView;
    }


    @GetMapping("/course-admin/edit/{id}")
    public ModelAndView courseEditing(@PathVariable Long id, Principal principal, HttpSession session   ) {

        ModelAndView modelAndView = new ModelAndView();
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
            if ( !user.getRole().getName().equals("ADMIN") && !user.getRole().getName().equals("MANAGER") && !user.getRole().getName().equals("TEACHER")) {
                session.setAttribute("error", "You do not have permission to access this page");
                modelAndView.setViewName("redirect:/dashboard");
                return modelAndView;
            }
        } else {
            return new ModelAndView("redirect:/login");
        }
        Course course2 = courseService.findById(id).get();
        Set<Subject> courseSubjectSet = new HashSet<>(subjectService.getAllSubjects());
        courseSubjectSet.retainAll(new HashSet<>(subjectService.getAllByStatus(true)));
        List<User> teacherList = userService.getAllUsers();
        teacherList.removeIf(user -> !user.getRole().getName().contains("TEACHER") && !user.getRole().getName().contains("MANAGER") || !user.getRole().isStatus());
        List<Role> semesterList = roleService.findAll();
        semesterList.removeIf(role -> !role.getSpeciality().contains("Semester") || !role.isStatus());
        teacherList.remove(course2.getTeacher());
        courseSubjectSet.remove(course2.getSubject());
        semesterList.remove(course2.getRole());
        modelAndView.setViewName("course-detail2");
        modelAndView.addObject("course", course2);
        modelAndView.addObject("subjectSet", courseSubjectSet);
        modelAndView.addObject("teacherSet", teacherList);
        modelAndView.addObject("semesterSet", semesterList);
        return modelAndView;
    }

    @PostMapping("/course-admin/edit/{id}")
    public String courseEditing(Model model, @RequestParam(value = "semesterId", required = false) Long semesterId, @RequestParam(value = "subjectId", required = false) Long subjectId, @RequestParam(value = "teacher", required = false) Long teacher, @PathVariable Long id, @RequestParam String name, @RequestParam boolean status, @RequestParam String description, Principal principal) {
        boolean hasError = false;
        Course course2 = courseService.findById(id).get();

        if (name == null || name.isEmpty()) {
            model.addAttribute("errorName", "Name cannot be null");
            hasError = true;
        } else if (name.length() > 20) {
            model.addAttribute("errorName", "Name cannot exceed 20 characters");
            hasError = true;
        }
        if (description == null) {
            model.addAttribute("errorDescription", "Description cannot be null");
            hasError = true;
        }
        if (semesterId == null) {
            for (Course course : courseService.findAllByRole_Id(course2.getRole().getId())) {
                if (course.getCourseName().equals(name)) {
                    if (!Objects.equals(course.getId(), course2.getId())) {
                        model.addAttribute("errorName", "Name already exists in this semester");
                        hasError = true;
                    }
                }
            }
        } else {
            for (Course course : courseService.findAllByRole_Id(semesterId)) {
                if (course.getCourseName().equals(name)) {
                    model.addAttribute("errorName", "Name already exists in this semester");
                    hasError = true;
                }
            }
        }
        if (hasError) {
            Set<Subject> courseSubjectSet = new HashSet<>(subjectService.getAllSubjects());
            courseSubjectSet.retainAll(new HashSet<>(subjectService.getAllByStatus(true)));
            List<User> teacherList = userService.getAllUsers();
            teacherList.removeIf(user -> !user.getRole().getName().contains("TEACHER") && !user.getRole().getName().contains("MANAGER") || !user.getRole().isStatus());
            List<Role> semesterList = roleService.findAll();
            semesterList.removeIf(role -> !role.getSpeciality().contains("Semester") || !role.isStatus());
            teacherList.remove(course2.getTeacher());
            courseSubjectSet.remove(course2.getSubject());
            semesterList.remove(course2.getRole());
            model.addAttribute("course", course2);
            model.addAttribute("teacherSet", teacherList);
            model.addAttribute("subjectSet", courseSubjectSet);
            model.addAttribute("semesterSet", semesterList);
            return "course-detail2";
        }
        courseService.updateCourse(id, name.trim(), description.trim(), status, subjectService.getSubjectById(subjectId), userService.findById(teacher), roleService.getRoleById(semesterId).get());
        return "redirect:/course-admin";
    }

    @RequestMapping("/course-admin/edit2/{id}/page={currentPage}/size={totalPages}")
    public String editStatusDirect(@PathVariable Long id, @PathVariable int currentPage, @PathVariable int totalPages) {

        Course course = courseService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));
        courseService.updateByStatusId(id, !course.isStatus());
        return "redirect:/course-admin?page=" + currentPage + "&size=" + 9;
    }

    @GetMapping("/course-admin")
    public ModelAndView courseForAdmin(HttpSession session, @RequestParam(value = "subjectId", required = false) Long subjectId, @RequestParam(value = "column", required = false) String column, @RequestParam(value = "order", required = false) String direction, @RequestParam(value = "courseSearch", required = false) String courseSearch, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "9") int size) {

        ModelAndView modelAndView = new ModelAndView();
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
            if ( !user.getRole().getName().equals("ADMIN") && !user.getRole().getName().equals("MANAGER") && !user.getRole().getName().equals("TEACHER")) {
                session.setAttribute("error", "You do not have permission to access this page");
                modelAndView.setViewName("redirect:/dashboard");
                return modelAndView;
            }
        } else {
            return new ModelAndView("redirect:/login");
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Course> courseList = null;
        session.setAttribute("courseSearch", courseSearch);
        if (courseSearch != null && (subjectId == null || subjectId.describeConstable().isEmpty())) {
            courseList = courseService.findByCourseNameContaining(pageable, courseSearch, column, direction);
        } else if (courseSearch != null && subjectId != null) {
            courseList = courseService.findAllBySubject_IdAndCourseNameContaining(pageable, subjectId, courseSearch, column, direction);
        } else {
            courseList = courseService.findAll(pageable);
        }
        Set<Subject> courseSubjectSet = new HashSet<>();
        Set<User> teacherSet = new HashSet<>();
        for (Course course : courseService.getAllCourse()) {
            courseSubjectSet.add(course.getSubject());
            teacherSet.add(course.getTeacher());
        }

        courseSubjectSet.retainAll(new HashSet<>(subjectService.getAllByStatus(true)));
        teacherSet.retainAll(new HashSet<>(userService.getAllUsers()));
        List<Role> semesterList = roleService.findAll();
        semesterList.removeIf(role -> !role.getSpeciality().contains("Semester") || !role.isStatus());
        modelAndView.setViewName("course-table-view");
        modelAndView.addObject("semesterSet", semesterList);
        modelAndView.addObject("teacherSet", teacherSet);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", courseList.getTotalPages());
        modelAndView.addObject("courseList", courseList.getContent());
        modelAndView.addObject("subjectSet", courseSubjectSet);
        modelAndView.addObject("user", userService.findByUser(principal.getName()));
        return modelAndView;
    }


    @RequestMapping("/course-admin/search")
    public ModelAndView searchCourseAdminSubjectName(HttpSession session, @RequestParam(value = "semesterId", required = false) Long role, @RequestParam(value = "teacher", required = false) Long teacher, @RequestParam(value = "column", required = false) String column, @RequestParam(value = "order", required = false) String direction, @RequestParam(value = "status", required = false) Boolean status, @RequestParam(value = "courseName", required = false, defaultValue = "") String courseName, @RequestParam(value = "subjectId", required = false) Long subjectId, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "9") int size) {

        ModelAndView modelAndView = new ModelAndView();
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
            if ( !user.getRole().getName().equals("ADMIN") && !user.getRole().getName().equals("MANAGER") && !user.getRole().getName().equals("TEACHER")) {
                session.setAttribute("error", "You do not have permission to access this page");
                modelAndView.setViewName("redirect:/dashboard");
                return modelAndView;
            }
        } else {
            return new ModelAndView("redirect:/login");
        }
        Pageable pageable = PageRequest.of(page, size);
        session.setAttribute("courseName", courseName);
        Page<Course> courseList = courseService.findAllByNameContainingIgnoreCaseAndStatusAndRoleIdAndUserIdAndSubjectId(courseName, status, role, teacher, subjectId, pageable, column, direction);
        Set<Subject> courseSubjectSet = new HashSet<>();
        Set<User> teacherSet = new HashSet<>();
        for (Course course : courseService.getAllCourse()) {
            courseSubjectSet.add(course.getSubject());
            teacherSet.add(course.getTeacher());
        }
        courseSubjectSet.retainAll(new HashSet<>(subjectService.getAllByStatus(true)));
        teacherSet.retainAll(new HashSet<>(userService.getAllUserByStatus(true)));
        List<Role> semesterList = roleService.findAll();
        semesterList.removeIf(role2 -> !role2.getSpeciality().contains("Semester") || !role2.isStatus());
        modelAndView.setViewName("course-table-view");
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("semesterSet", semesterList);
        modelAndView.addObject("teacherSet", teacherSet);
        modelAndView.addObject("totalPages", courseList.getTotalPages());
        modelAndView.addObject("courseList", courseList.getContent());
        modelAndView.addObject("subjectSet", courseSubjectSet);
        modelAndView.addObject("user", userService.findByUser(principal.getName()));
        return modelAndView;
    }




}
