    package com.krs.knowledgerevisingsystem.controller;

    import com.krs.knowledgerevisingsystem.entity.Course;
    import com.krs.knowledgerevisingsystem.entity.Role;
    import com.krs.knowledgerevisingsystem.entity.Subject;
    import com.krs.knowledgerevisingsystem.entity.User;
    import com.krs.knowledgerevisingsystem.service.*;
    import jakarta.servlet.http.HttpSession;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageImpl;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.servlet.ModelAndView;

    import java.security.Principal;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Objects;
    import java.util.Set;
    import java.util.stream.Collectors;

    @Controller
    public class MyCourseTeacherController {
        private CourseService courseService;
        private UserService userService;
        private SubjectService subjectService;
        private EnrollmentService enrollmentService;
        private LessonService lessonService;
        private RoleService roleService;


        public MyCourseTeacherController(RoleService roleService, LessonService lessonService, UserService userService, SubjectService subjectService, CourseService courseService, EnrollmentService enrollmentService) {
            this.subjectService = subjectService;
            this.userService = userService;
            this.courseService = courseService;
            this.lessonService = lessonService;
            this.enrollmentService = enrollmentService;
            this.roleService = roleService;
        }

        @GetMapping("/teacher-course")
        public ModelAndView courseForAdmin(HttpSession session, @RequestParam(value = "subjectId", required = false) Long subjectId, @RequestParam(value = "column", required = false) String column, @RequestParam(value = "order", required = false) String direction, @RequestParam(value = "courseSearch", required = false) String courseSearch, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "9") int size) {
            ModelAndView modelAndView = new ModelAndView();
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

            User user = userService.findByUser(principal.getName());
            List<Course> filteredCourses = courseList.stream()
                    .filter(course -> Objects.equals(course.getTeacher().getId(), user.getId()))
                    .collect(Collectors.toList());
            courseList = new PageImpl<>(filteredCourses, pageable, filteredCourses.size());
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
            modelAndView.setViewName("teacher-course");
            modelAndView.addObject("semesterSet", semesterList);
            modelAndView.addObject("teacherSet", teacherSet);
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", courseList.getTotalPages());
            modelAndView.addObject("courseList", courseList.getContent());
            modelAndView.addObject("subjectSet", courseSubjectSet);
            modelAndView.addObject("user", userService.findByUser(principal.getName()));
            return modelAndView;
        }


        @RequestMapping("/teacher-course/search")
        public ModelAndView searchCourseAdminSubjectName(HttpSession session, @RequestParam(value = "semesterId", required = false) Long role, @RequestParam(value = "teacher", required = false) Long teacher, @RequestParam(value = "column", required = false) String column, @RequestParam(value = "order", required = false) String direction, @RequestParam(value = "status", required = false) Boolean status, @RequestParam(value = "courseName", required = false) String courseName, @RequestParam(value = "subjectId", required = false) Long subjectId, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "9") int size) {
            ModelAndView modelAndView = new ModelAndView();
            Pageable pageable = PageRequest.of(page, size);
            session.setAttribute("courseName", courseName);
            Page<Course> courseList = null;
            if (courseName != null && subjectId == null && status == null) {
                courseList = courseService.findByCourseNameContaining(pageable, courseName, column, direction);
            } else if (courseName != null && subjectId != null && status == null) {
                courseList = courseService.findAllBySubject_IdAndCourseNameContaining(pageable, subjectId, courseName, column, direction);
            } else if (courseName != null && status != null && subjectId == null) {
                courseList = courseService.findAllByStatusAndCourseNameContaining(pageable, status, courseName, column, direction);
            } else if (courseName != null && status != null && subjectId != null) {
                courseList = courseService.findAllBySubject_IdAndStatusAndCourseNameContaining(pageable, subjectId, status, courseName, column, direction);
            } else {
                courseList = courseService.findAllByStatus(pageable, true);
            }

            if (role != null) {
                Role role1 = roleService.getRoleById(role).get();
                List<Course> filteredCourses = courseList.stream()
                        .filter(course -> Objects.equals(course.getRole().getId(), role1.getId()))
                        .collect(Collectors.toList());
                courseList = new PageImpl<>(filteredCourses, pageable, filteredCourses.size());
            }

            User user = userService.findByUser(principal.getName());
            List<Course> filteredCourses = courseList.stream()
                    .filter(course -> Objects.equals(course.getTeacher().getId(), user.getId()))
                    .collect(Collectors.toList());
            courseList = new PageImpl<>(filteredCourses, pageable, filteredCourses.size());


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
            modelAndView.setViewName("teacher-course");
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
