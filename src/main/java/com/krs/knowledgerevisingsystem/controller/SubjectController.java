package com.krs.knowledgerevisingsystem.controller;

import com.krs.knowledgerevisingsystem.entity.*;
import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import com.krs.knowledgerevisingsystem.exception.ManageUser.UserInvalidException;
import com.krs.knowledgerevisingsystem.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class SubjectController {

    private final SubjectService subjectService;
    private final RoleService roleService;
    private final LessonService lessonService;
    private final UserService userService;
    private final SubjectManagerService subjectManagerService;


    public SubjectController(SubjectManagerService subjectManagerService, RoleService roleService, SubjectService subjectService, LessonService lessonService, UserService userService) {
        this.subjectService = subjectService;
        this.roleService = roleService;
        this.lessonService = lessonService;
        this.userService = userService;
        this.subjectManagerService = subjectManagerService;
    }

    @RequestMapping("/subject")
    ModelAndView setting(HttpSession session,Principal principal, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "9") int size) {
        ModelAndView modelAndView = new ModelAndView();
        if (principal != null) {

            User user = userService.findByUser(principal.getName());
            if (!user.getRole().getName().equals("ADMIN")) {
                session.setAttribute("error", "You do not have permission to access this page");
                modelAndView.setViewName("redirect:/dashboard");
                return modelAndView;
            }
        } else {
            return new ModelAndView("redirect:/login");
        }
        Pageable pageable = PageRequest.of(page, size);
        modelAndView.setViewName("subject");
//        Page<SubjectManager> subjectList = subjectManagerService.findAll(pageable);
        Page<Subject> subjectList = subjectService.findAll(pageable);
        Set<Role> temp = new HashSet<>();
        for (Role role : roleService.findAll()) {
            temp.add(role);
            if (role.getSpeciality().equals("User Role") || role.getSpeciality().equals("Semester Speciality") || !role.isStatus()) {
                temp.remove(role);
            }
        }
        Set<Role> specialitySet = new HashSet<>(temp);
        Set<Long> temp2 = new HashSet<>();
        Set<User> managerSet = new HashSet<>();
        for (SubjectManager role : subjectManagerService.findAll()) {
            temp2.add(role.getUser().getId());
        }
        for (Long id : temp2) {
            managerSet.add(userService.findById(id));
        }
        modelAndView.addObject("managerSet", managerSet);
        modelAndView.addObject("specialitySet", specialitySet);
        modelAndView.addObject("subjectList", subjectList.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", subjectList.getTotalPages());
        return modelAndView;
    }

    @RequestMapping("/subject/search")
    public ModelAndView subjectSearch(Principal principal, HttpSession session,
                                      @RequestParam(value = "manager", required = false) Long manager,
                                      @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                      @RequestParam(value = "speciality", required = false) Long speciality,
                                      @RequestParam(value = "status", required = false) String status,
                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "9") int size) {

        ModelAndView modelAndView = new ModelAndView();
        Pageable pageable = PageRequest.of(page, size);
        session.setAttribute("name", name);
        if (principal == null) {
            return new ModelAndView("redirect:/login");
        }
        User user = userService.findByUser(principal.getName());
        if (!user.getRole().getName().equals("MANAGER") && !user.getRole().getName().equals("ADMIN")) {
            session.setAttribute("error", "You do not have permission to access this page");
            modelAndView.setViewName("redirect:/dashboard");
            return modelAndView;
        }

        Boolean statusBool = Boolean.parseBoolean(status);
        if (status == null || status.isEmpty() ) {
            statusBool = null;
            System.out.println(statusBool);
        }
        Page<Subject> subjectList = subjectService.findByStatusAndRole_IdAndNameContainingOrCodeContainingAndSubjectManagerContaining(statusBool,speciality, name, name,manager, pageable);

        // Gather specialities
        Set<Role> specialitySet = subjectService.getAllSubjects().stream()
                .map(Subject::getRole)
                .filter(role -> !role.getSpeciality().equals("User Role") && !role.getSpeciality().equals("Semester Speciality") && role.isStatus())
                .collect(Collectors.toSet());

        // Gather managers
        Set<User> managerSet = subjectManagerService.findAll().stream()
                .map(SubjectManager::getUser)
                .collect(Collectors.toSet());

        // Set model attributes
        modelAndView.setViewName("subject");
        modelAndView.addObject("managerSet", managerSet);
        modelAndView.addObject("specialitySet", specialitySet);
        modelAndView.addObject("subjectList", subjectList.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", subjectList.getTotalPages());

        return modelAndView;
    }

    @GetMapping("/subject/adding")
    public ModelAndView subjectAdd(Principal principal, HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
            if (!user.getRole().getName().equals("ADMIN")) {
                session.setAttribute("error", "You do not have permission to access this page");
                modelAndView.setViewName("redirect:/dashboard");
                return modelAndView;
            }
        } else {
            return new ModelAndView("redirect:/login");
        }
        Set<Role> temp = new HashSet<>();
        for (Role role : roleService.findAll()) {
            if (!role.getSpeciality().equals("User Role") && !role.getSpeciality().equals("Semester Speciality") && role.isStatus()) {
                temp.add(role);
            }
        }
        Set<Role> specialitySet = new HashSet<>(temp);
        List<User> managerList = userService.getUserByRole("MANAGER");
        modelAndView.setViewName("subject-adding");
        modelAndView.addObject("specialitySet", specialitySet);
        modelAndView.addObject("managerList", managerList);
        return modelAndView;
    }

    @RequestMapping("/subject/adding")
    public String subjectAdd2(Principal principal, HttpSession session, Model model, @RequestParam(value = "lessonName", required = false) List<String> lessonName, @RequestParam(value = "status", required = false) boolean status, @RequestParam(value = "manager", required = false) Long manger, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "code", required = false) String code, @RequestParam(value = "speciality", required = false) Long speciality, @RequestParam(value = "description", required = false) String description) throws UserInvalidException {
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
            if (!user.getRole().getName().equals("ADMIN")) {
                session.setAttribute("error", "You do not have permission to access this page");

                return "redirect:/dashboard";
            }
        } else {
            return"redirect:/login";
        }
        Subject subject = new Subject();
        subject.setCode(code);
        subject.setName(name);
        subject.setRole(roleService.getRoleById(speciality).get());
        subject.setDescription(description);
        subject.setStatus(status);
        boolean hasError = false;

        if (name == null || name.isEmpty()) {
            model.addAttribute("errorName", "Name cannot be null");
            hasError = true;
        } else if (name.length() > 20) {
            model.addAttribute("errorName", "Name cannot exceed 20 characters");
            hasError = true;
        }

        if (code == null || code.isEmpty()) {
            model.addAttribute("errorCode", "Code cannot be null");
            hasError = true;
        } else if (code.length() > 20) {
            model.addAttribute("errorCode", "Code cannot exceed 20 characters");
            hasError = true;
        }

        if (speciality == null) {
            model.addAttribute("errorSpeciality", "Speciality cannot be null");
            hasError = true;
        }

        if (description == null) {
            model.addAttribute("errorDescription", "Description cannot be null");
            hasError = true;
        }

        Set<Role> temp = new HashSet<>();
        for (Role role : roleService.findAll()) {
            if (!role.getSpeciality().equals("User Role") && !role.getSpeciality().equals("Semester Speciality")&& role.isStatus()) {
                temp.add(role);
            }
        }
        Set<Role> specialitySet = new HashSet<>(temp);

        List<User> managerList = userService.getUserByRole("MANAGER");

        if (hasError) {
            model.addAttribute("errorMessage", "Duplicate entry for Code course");
            model.addAttribute("specialitySet", specialitySet);
            model.addAttribute("managerList", managerList);
            return "subject-adding";
        }
        try {
            subjectService.saveSubject(subject);
            subjectManagerService.save(subject, userService.findUserById(manger));
            return "redirect:/subject";
        } catch (DataIntegrityViolationException e) {

            model.addAttribute("errorMessage", "Duplicate entry for Code course");
            model.addAttribute("specialitySet", specialitySet);
            model.addAttribute("managerList", managerList);
            return "subject-adding";
        }
    }


    @GetMapping("/subject/{id}")
    public ModelAndView editSubject(@PathVariable Long id, Principal principal,HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Subject subject = subjectService.getSubjectById(id);
        List<Lesson> lessonList = new ArrayList<>();
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
        Set<Role> temp = new HashSet<>();
        for (Role role2 : roleService.findAll()) {
            if (!role2.getSpeciality().equals("User Role") && !role2.getSpeciality().equals("Semester Speciality")&& role2.isStatus()) {
                temp.add(role2);
            }
        }
        Set<Role> specialitySet = new HashSet<>(temp);
        List<User> managerList = userService.getUserByRole("MANAGER");
        managerList.removeIf(user -> user.equals(subject.getSubjectManager().get(0).getUser()));
        modelAndView.setViewName("subject-detail");
        modelAndView.addObject("managerSubject", subject.getSubjectManager().get(0));
        modelAndView.addObject("subject", subject);
        modelAndView.addObject("managerList", managerList);
        modelAndView.addObject("specialitySet", specialitySet);
        modelAndView.addObject("lessonList", lessonList);

        return modelAndView;
    }

    @RequestMapping("/subject/edit/{id}")
    public String editSubjectDetail(Model model, @PathVariable Long id,
                                    @RequestParam(value = "lesson", required = false) String lesson,
                                    @RequestParam(value = "manager", required = false) Long manager,
                                    @RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "code", required = false) String code,
                                    @RequestParam(value = "speciality", required = false) Long speciality,
                                    @RequestParam(value = "description", required = false) String description,
                                    @RequestParam(value = "status", required = false) boolean status,
                                    @RequestParam(value = "newLesson", required = false) List<String> newLesson,
                                    @ModelAttribute Subject subject, Principal principal, HttpSession session
    ) {
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
            if (!user.getRole().getName().equals("ADMIN")) {
                session.setAttribute("error", "You do not have permission to access this page");

                return "redirect:/dashboard";
            }
        } else {
            return"redirect:/login";
        }
        List<Lesson> list = subject.getLessons();
        Role role = roleService.getRoleById(speciality).get();
        boolean hasError = false;
        if (name == null || name.isEmpty()) {
            model.addAttribute("errorName", "Name cannot be null");
            hasError = true;
        } else if (name.length() > 20) {
            model.addAttribute("errorName", "Name cannot exceed 20 characters");
            hasError = true;
        }

        if (code == null || code.isEmpty()) {
            model.addAttribute("errorCode", "Code cannot be null");
            hasError = true;
        } else if (code.length() > 20) {
            model.addAttribute("errorCode", "Code cannot exceed 20 characters");
            hasError = true;
        }

        if (description == null) {
            model.addAttribute("errorDescription", "Description cannot be null");
            hasError = true;
        }

        Set<Role> temp = new HashSet<>();
        for (Role role2 : roleService.findAll()) {
            if (!role2.getSpeciality().equals("User Role") && !role2.getSpeciality().equals("Semester Speciality")&& role2.isStatus()) {
                temp.add(role2);
            }
        }
        Set<Role> specialitySet = new HashSet<>(temp);
        List<User> managerList = userService.getUserByRole("MANAGER");
        managerList.removeIf(user -> user.equals(subjectService.getSubjectById(id).getSubjectManager().get(0).getUser()));

        if (hasError) {
            model.addAttribute("subject", subjectService.getSubjectById(id));
            model.addAttribute("managerSubject", subjectService.getSubjectById(id).getSubjectManager().get(0));
            model.addAttribute("managerList", managerList);
            model.addAttribute("specialitySet", specialitySet);
            return "subject-detail";
        }
        try {
            subjectService.updateUsingSaveMethod(id, code, name, role, description, status);
            subjectManagerService.updateManager(id, manager);

            model.addAttribute("list", list);
            return "redirect:/subject";
        } catch (DataIntegrityViolationException | SaveAccountInvalidException e) {

            model.addAttribute("errorMessage", "Duplicate entry for Code course");
            model.addAttribute("subject", subjectService.getSubjectById(id));
            model.addAttribute("managerSubject", subjectService.getSubjectById(id).getSubjectManager().get(0));
            model.addAttribute("managerList", managerList);
            model.addAttribute("specialitySet", specialitySet);
            return "subject-detail";
        }
    }

    @RequestMapping("/subject/edit2/{id}/page={currentPage}/size={totalPages}")
    public String editStatusDirect(@PathVariable Long id, @PathVariable int currentPage, HttpSession session, Principal principal) {
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
            if (!user.getRole().getName().equals("ADMIN")) {
                session.setAttribute("error", "You do not have permission to access this page");

                return "redirect:/dashboard";
            }
        } else {
            return"redirect:/login";
        }
        Subject subject = subjectService.getSubjectById(id);
        System.out.println("Subject ID: " + id); // This will print the id in the console
        subjectService.updateByStatusId(id, !subject.isStatus());
        return "redirect:/subject?page=" + currentPage + "&size=" + 9;
    }
}
