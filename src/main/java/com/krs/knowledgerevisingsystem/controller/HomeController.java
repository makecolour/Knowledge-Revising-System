package com.krs.knowledgerevisingsystem.controller;

import com.krs.knowledgerevisingsystem.dto.EnrollmentDTO;
import com.krs.knowledgerevisingsystem.dto.ExamAttemptCount;
import com.krs.knowledgerevisingsystem.entity.*;
import com.krs.knowledgerevisingsystem.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final UserService userService;
    private final SubjectService subjectService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final RoleService roleService;
    private final ExamService examService;
    private final AttemptService attemptService;
    private final LessonService lessonService;

    public HomeController(RoleService roleService,
                          EnrollmentService enrollmentService,
                          CourseService courseService,
                          UserService userService,
                          SubjectService subjectService,
                          ExamService examService,
                          AttemptService attemptService,
                          LessonService lessonService) {
        this.userService = userService;
        this.subjectService = subjectService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.roleService = roleService;
        this.examService = examService;
        this.attemptService = attemptService;
        this.lessonService = lessonService;
    }

    //if signed in go to dashboard, else go to homepage
    @GetMapping({"/home", "/",""})
    ModelAndView index(Principal principal) {
        ModelAndView mav = new ModelAndView();
        User user = null;
        List<EnrollmentDTO> popularCouseList = enrollmentService.findTopCoursesByEnrollments();

        if (principal != null) {
            user = userService.findByUser(principal.getName());
        }

        List<Course> courseList = new ArrayList<>();
        if (popularCouseList != null && !popularCouseList.isEmpty()) {
//            int size = Math.min(8, popularCouseList.size());
            for (int i = 0; i < popularCouseList.size(); i++) {
                Long courseId = popularCouseList.get(i).getCourseId();
                Optional<Course> optionalCourse = courseService.findById(courseId);
                if (optionalCourse.isPresent()) {
                    courseList.add(optionalCourse.get());
                } else {
                    System.out.println("Course not found for ID: " + courseId);
                }
            }
        }

        Set<Subject> courseSubjectSet = new HashSet<>();

        for (Course course : courseService.getAllCourse()) {
            courseSubjectSet.add(course.getSubject());
        }
        courseSubjectSet.retainAll(new HashSet<>(subjectService.getAllByStatus(true)));

        List<Role> semesterList = roleService.findAll();
        semesterList.removeIf(role2 -> !role2.getSpeciality().contains("Semester") || !role2.isStatus());

        mav.setViewName("homepage");
        mav.addObject("courseList", courseList);
        mav.addObject("subjectSet", courseSubjectSet);
        mav.addObject("semesterSet", semesterList);
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping("/course-popular/search")
    ModelAndView searchcourse(Principal principal, @RequestParam(value = "subjectId", required = false) Long subjectId, @RequestParam(value = "semesterId", required = false) Long semesterId) {
        ModelAndView mav = new ModelAndView();
        User user = null;
        List<EnrollmentDTO> popularCouseList = enrollmentService.findTopCoursesByEnrollments();
        if (principal != null) {
            user = userService.findByUser(principal.getName());
        }
        List<Course> courseList = new ArrayList<>();
        if (popularCouseList != null && !popularCouseList.isEmpty()) {
            for (int i = 0; i < popularCouseList.size(); i++) {
                Long courseId = popularCouseList.get(i).getCourseId();
                Optional<Course> optionalCourse = courseService.findById(courseId);
                if (optionalCourse.isPresent()) {
                    courseList.add(optionalCourse.get());
                } else {
                    System.out.println("Course not found for ID: " + courseId);
                }
            }
        }
        if (subjectId != null) {
            courseList = courseList.stream()
                    .filter(course -> Objects.equals(course.getSubject().getId(), subjectId))
                    .collect(Collectors.toList());
        }
        if (semesterId != null) {
            Role role1 = roleService.getRoleById(semesterId).get();
            courseList = courseList.stream()
                    .filter(course -> Objects.equals(course.getRole().getId(), role1.getId()))
                    .collect(Collectors.toList());
        }

        Set<Subject> courseSubjectSet = new HashSet<>();
        for (Course course : courseService.getAllCourse()) {
            courseSubjectSet.add(course.getSubject());
        }
        courseSubjectSet.retainAll(new HashSet<>(subjectService.getAllByStatus(true)));
        List<Role> semesterList = roleService.findAll();
        semesterList.removeIf(role2 -> !role2.getSpeciality().contains("Semester") || !role2.isStatus());

        mav.setViewName("homepage");
        mav.addObject("courseList", courseList);
        mav.addObject("user", user);
        mav.addObject("subjectSet", courseSubjectSet);
        mav.addObject("semesterSet", semesterList);
        return mav;
    }


    @GetMapping("/landing")
    ModelAndView index2(Principal principal) {

        ModelAndView mav = new ModelAndView();
        User user = null;
        List<EnrollmentDTO> popularCouseList = enrollmentService.findTopCoursesByEnrollments();

        if (principal != null) {
            user = userService.findByUser(principal.getName());
        }
        List<Course> courseList = new ArrayList<>();
        if (popularCouseList != null && !popularCouseList.isEmpty()) {
            int size = Math.min(4, popularCouseList.size());
            for (int i = 0; i < size; i++) {
                Long courseId = popularCouseList.get(i).getCourseId();
                Optional<Course> optionalCourse = courseService.findById(courseId);
                if (optionalCourse.isPresent()) {
                    courseList.add(optionalCourse.get());
                } else {
                    System.out.println("Course not found for ID: " + courseId);
                }
            }
        }

        mav.setViewName("landing-page");
        mav.addObject("courseList", courseList);

        mav.addObject("user", user);
        return mav;
    }

    @GetMapping("/dashboard")
    ModelAndView dashboard(Principal principal, HttpSession session ) {
        ModelAndView mav = new ModelAndView("dashboard");
        User user = userService.findByUser(principal.getName());


        mav.setViewName("dashboard");
        mav.addObject("user", user);
        switch (user.getRole().getName()) {
            case "ADMIN":
                List<String> roles = Arrays.asList("ADMIN", "MANAGER", "TEACHER", "STUDENT");

                for (String role : roles) {
                    mav.addObject(role + "Count", userService.getUserByRole(role).size());
                }

                mav.addObject("totalUsers", userService.getAllUsers().size());
                mav.addObject("roleCounts", roleService.findAll().size());
                mav.addObject("courseCounts", courseService.getAllCourse().size());
                mav.addObject("examCount", examService.getAllExam().size());
                mav.addObject("questionCount", examService.getAllQuestion().size());
                mav.addObject("totalAttempt", attemptService.findAll().size());

                Optional<ExamAttemptCount> examAttemptCount = attemptService.findExamWithMostAttempts();
                if (examAttemptCount.isPresent()) {
                    mav.addObject("mostAttempts", examAttemptCount.get().getExamName());
                    mav.addObject("mostAttemptsCount", examAttemptCount.get().getCount());
                } else {
                    mav.addObject("mostAttempts", "No attempts found");
                    mav.addObject("mostAttemptsCount", 0);
                }

                mav.addObject("totalLesson", lessonService.getAll().size());
                mav.addObject("totalSS", lessonService.findAllStudySet().size());

                break;
            case "TEACHER":
                // TODO - Add Course Later
                break;
            case "STUDENT":
                // TODO - Add Course Later
                break;
            default:
                break;
        }

        return mav;
    }
//    @GetMapping("/contact")
//    ModelAndView contact() {
//        return new ModelAndView("contact");
//    }
//
//    @PostMapping("/sendContact")
//    ModelAndView sendContact(@RequestParam String phone, @RequestParam String email, @RequestParam String subject, @RequestParam String message) {
//        //TODO do backend stuff here
//        return new ModelAndView("contact");
//    }
}
