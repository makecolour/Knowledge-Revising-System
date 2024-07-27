package com.krs.knowledgerevisingsystem.controller;

import com.krs.knowledgerevisingsystem.entity.*;
import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import com.krs.knowledgerevisingsystem.service.*;
import jakarta.servlet.http.HttpSession;
import org.apache.http.protocol.HTTP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class LessonController {
    private CourseService courseService;
    private SubjectManagerService subjectManagerService;
    private UserService userService;
    private SubjectService subjectService;
    private EnrollmentService enrollmentService;
    private LessonService lessonService;
    private RoleService roleService;


    public LessonController(SubjectManagerService subjectManagerService, RoleService roleService, LessonService lessonService, UserService userService, SubjectService subjectService, CourseService courseService, EnrollmentService enrollmentService) {
        this.subjectService = subjectService;
        this.subjectManagerService = subjectManagerService;
        this.courseService = courseService;
        this.lessonService = lessonService;
        this.enrollmentService = enrollmentService;
        this.roleService = roleService;
        this.userService = userService;
    }

    @RequestMapping("/lesson")
    ModelAndView search(@RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "9") int size,
                        @RequestParam(value = "subjectId", required = false) Long subjectId,
                        Principal principal, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
            if (!user.getRole().getName().equals("MANAGER") && !user.getRole().getName().equals("ADMIN")) {
                session.setAttribute("error", "You do not have permission to access this page");
                modelAndView.setViewName("redirect:/dashboard");
                return modelAndView;
            }
        } else {
            return new ModelAndView("redirect:/login");
        }
        Pageable pageable = PageRequest.of(page, size);
        modelAndView.setViewName("lesson-list2");
        List<Subject> temp = new ArrayList<>();
        for (Subject course : subjectService.getAllSubjects()) {
            if (lessonService.findAllBySubject_Id(course.getId(), 9, pageable).getTotalElements() > 0) {
                temp.add(course);
            }
        }
        if (subjectId == null && !temp.isEmpty()) {
            subjectId = temp.get(0).getId();
        }
        Page<Lesson> lessonList = lessonService.findAllBySubject_Id(subjectId, 9, pageable);
        Set<Subject> subjectSet = new HashSet<>(temp);
        subjectSet.retainAll(new HashSet<>(subjectService.getAllByStatus(true)));
        modelAndView.addObject("subjectSet", subjectSet);
        modelAndView.addObject("lessonList", lessonList.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", lessonList.getTotalPages());
        modelAndView.addObject("subjectId", subjectId);
        return modelAndView;
    }

    @RequestMapping("/lesson/search")
    public ModelAndView search(HttpSession session,
                               @RequestParam(value = "order", required = false) String order,
                               @RequestParam(value = "column", required = false) String column,
                               @RequestParam(value = "lessonName", required = false) String name,
                               @RequestParam(value = "subjectId", required = false) Long subjectId,
                               @RequestParam(value = "status", required = false) String status,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "9") int size, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Pageable pageable = PageRequest.of(page, size);
        Page<Lesson> lessonList = null;

        session.setAttribute("name", name);
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
            if (!user.getRole().getName().equals("MANAGER") && !user.getRole().getName().equals("ADMIN")) {
                session.setAttribute("error", "You do not have permission to access this page");
                modelAndView.setViewName("redirect:/dashboard");
                return modelAndView;
            }
        } else {
            return new ModelAndView("redirect:/login");
        }
        List<Subject> temp = new ArrayList<>();
        for (Subject course : subjectService.getAllSubjects()) {
            if (lessonService.findAllBySubject_Id(course.getId(), 9, pageable).getTotalElements() > 0) {
                temp.add(course);
            }
        }
        if (subjectId == null && !temp.isEmpty()) {
            subjectId = temp.get(0).getId();
        }

        if (name != null && (subjectId == null) && (status == null || status.isEmpty())) {
            lessonList = lessonService.findAllByNameContaining(name, pageable, column, order);
        } else if (name != null && subjectId != null && (status == null || status.isEmpty())) {
            lessonList = lessonService.findAllByNameContainingAndSubjectId(pageable, name, subjectId, column, order);
        } else if (name != null && (subjectId == null) && status != null && !status.isEmpty()) {
            boolean status2 = Boolean.parseBoolean(status);
            lessonList = lessonService.findAllByNameContainingAndStatus(pageable, name, status2, column, order);
        } else if (name != null && subjectId != null && status != null && !status.isEmpty()) {
            boolean status2 = Boolean.parseBoolean(status);
            lessonList = lessonService.findAllByNameContainingAndStatusAndSubjectId(pageable, name, status2, subjectId, column, order);
        } else {
            lessonList = lessonService.getAll(pageable);
        }

        modelAndView.setViewName("lesson-list2");
        Set<Subject> subjectSet = new HashSet<>(temp);
        subjectSet.retainAll(new HashSet<>(subjectService.getAllByStatus(true)));
        modelAndView.addObject("subjectSet", subjectSet);
        modelAndView.addObject("lessonList", lessonList.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", lessonList.getTotalPages());
        modelAndView.addObject("subjectId", subjectId); // Add subjectId to the model
        return modelAndView;
    }


    @RequestMapping("/lesson/edit2/{id}/page={currentPage}/size={totalPages}")
    public String editStatusDirect(@PathVariable Long id, @PathVariable int currentPage, @PathVariable int totalPages) {
        Lesson role = lessonService.findLessonById(id);
        lessonService.updateByStatusId(id, !role.isStatus());
        return "redirect:/lesson?page=" + currentPage + "&size=" + 9;
    }

    @GetMapping("/lesson-adding")
    public ModelAndView getLessonById(Principal principal, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
            if (!user.getRole().getName().equals("MANAGER") && !user.getRole().getName().equals("ADMIN")) {
                session.setAttribute("error", "You do not have permission to access this page");
                modelAndView.setViewName("redirect:/dashboard");
                return modelAndView;
            }
        } else {
            return new ModelAndView("redirect:/login");
        }
        Set<Subject> subjectSet = new HashSet<>();
        for (Subject course : subjectService.getAllSubjects()) {
            subjectSet.add(course);
        }
        subjectSet.retainAll(new HashSet<>(subjectService.getAllByStatus(true)));
        modelAndView.setViewName("lesson-adding");
        modelAndView.addObject("subjectSet", subjectSet);
        return modelAndView;
    }

    public String isValid(String name, Long subjectId) {
        if (name == null || name.isEmpty()) {
            return "Lesson name must not be empty";
        } else if (name.length() > 20) {
            return "Lesson name must not be more than 20 characters";
        }
        for (Lesson lesson : lessonService.getAllLessonSameSubject(subjectId)) {
            if (lesson.getName().equals(name)) {
                return "Lesson name must be unique";
            }
        }
        return "valid";
    }

    @PostMapping("/lesson-adding")
    public String addLesson(
            @RequestParam("subjectId") Long subjectId,
            @RequestParam(value = "lessonName", required = false) List<String> lessonName,
            @RequestParam(value = "lessonPriority", required = false) List<Integer> lessonPriority,
            @RequestParam(value = "status", required = false) boolean status,
            Model model) {
        Set<Subject> subjectSet = new HashSet<>();
        for (Subject course : subjectService.getAllSubjects()) {
            subjectSet.add(course);
        }
        subjectSet.retainAll(new HashSet<>(subjectService.getAllByStatus(true)));
        if (lessonName == null || lessonName.isEmpty()) {
            model.addAttribute("errorMessage2", "Lesson names must not be empty.");
            model.addAttribute("subjectSet", subjectSet);
            return "lesson-adding";  // Return to the same page with error message
        }

        Subject subject = subjectService.getSubjectById(subjectId);
        for (String lesson : lessonName) {
            String valid = isValid(lesson, subjectId);
            if (!valid.equals("valid")) {
                model.addAttribute("errorMessage2", valid);
                model.addAttribute("subjectSet", subjectSet);
                return "lesson-adding";  // Return to the same page with error message
            }
        }

        try {
            for (int i = 0; i < lessonName.size(); i++) {
                lessonService.save(lessonName.get(i), subject, lessonPriority.get(i), status);
            }

            return "redirect:/lesson";
        } catch (SaveAccountInvalidException e) {
            model.addAttribute("errorMessage2", "An error occurred while saving lessons: " + e.getMessage());
            model.addAttribute("subjectSet", subjectSet);
            return "lesson-adding";  // Return to the same page with error message
        }
    }

    @GetMapping("/lesson/edit/{id}")
    public ModelAndView uppdate(@PathVariable Long id, Principal principal, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
            if (!user.getRole().getName().equals("MANAGER") && !user.getRole().getName().equals("ADMIN")) {
                session.setAttribute("error", "You do not have permission to access this page");
                modelAndView.setViewName("redirect:/dashboard");
                return modelAndView;
            }
        } else {
            return new ModelAndView("redirect:/login");
        }
        Set<Subject> subjectSet = new HashSet<>();
        for (Subject course : subjectService.getAllSubjects()) {
            subjectSet.add(course);
        }
        subjectSet.retainAll(new HashSet<>(subjectService.getAllByStatus(true)));
        modelAndView.setViewName("lesson-detail");
        modelAndView.addObject("subjectSet", subjectSet);
        modelAndView.addObject("lesson", lessonService.findLessonById(id));
        return modelAndView;
    }

    @PostMapping("/lesson/edit/{id}")
    public String update(Principal principal, Model model, @RequestParam("priority") int priority, @RequestParam("name") String name, @RequestParam("subjectId") Long subjectId, @RequestParam(value = "status", required = false) String status, @PathVariable Long id) {

        Subject subject = subjectService.getSubjectById(subjectId);
        Set<Subject> subjectSet = new HashSet<>();
        for (Subject course : subjectService.getAllSubjects()) {
            subjectSet.add(course);
        }
        subjectSet.retainAll(new HashSet<>(subjectService.getAllByStatus(true)));
        Lesson lesson = lessonService.findLessonById(id);
        boolean status2 = "on".equals(status);

        if (name == null || name.isEmpty() || name.length() > 20) {
            model.addAttribute("errorMessage", "Lesson names must not be empty.");
            model.addAttribute("subjectSet", subjectSet);
            model.addAttribute("lesson", lesson);
            return "lesson-detail";  // Return to the same page with error message
        }

        if (priority <= 0) {
            model.addAttribute("errorMessage", "Priority must not be empty.");
            model.addAttribute("subjectSet", subjectSet);
            model.addAttribute("lesson", lesson);
            return "lesson-detail";  // Return to the same page with error message
        }

        for (Lesson lesson2 : lessonService.getAll()) {
            if (lesson2.getName().equals(name)) {
                if (!lesson2.getId().equals(id)) {
                    model.addAttribute("errorMessage", "Lesson name must be unique");
                    model.addAttribute("subjectSet", subjectSet);
                    model.addAttribute("lesson", lesson);
                    return "lesson-detail";  // Return to the same page with error message
                }
            }
        }
        lessonService.updateLesson(id, name, status2, subjectId, priority);
        return "redirect:/lesson";

    }

}
