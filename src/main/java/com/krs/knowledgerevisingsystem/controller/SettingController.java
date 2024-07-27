package com.krs.knowledgerevisingsystem.controller;

import com.krs.knowledgerevisingsystem.entity.*;

import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import com.krs.knowledgerevisingsystem.service.RoleService;
import com.krs.knowledgerevisingsystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SettingController {
    private final UserService userService;
    private final RoleService roleService;

    public SettingController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping("/setting")
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
        Set<String> temp = new HashSet<>();
        for (Role role : roleService.findAll()) {
            temp.add(role.getSpeciality());
        }
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
            if (!user.getRole().getName().equals("ADMIN")){
                return new ModelAndView("redirect:/dashboard");
            }
            modelAndView.addObject("user", user);
        } else {
           return new ModelAndView("redirect:/login");
        }
        Set<String> specialitySet = new HashSet<>(temp);
        modelAndView.setViewName("setting");
        Page<Role> settingList = roleService.findAll(pageable);
        modelAndView.addObject("specialitySet", specialitySet);
        modelAndView.addObject("settingList", settingList.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", settingList.getTotalPages());
        return modelAndView;
    }


    @RequestMapping("/setting/search")
    public ModelAndView search(Principal principal,HttpSession session, @RequestParam(value = "order", required = false) String order, @RequestParam(value = "column", required = false) String column, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "speciality", required = false) String speciality, @RequestParam(value = "status", required = false) String status, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "9") int size) {
        ModelAndView modelAndView = new ModelAndView();
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
            if ( !user.getRole().getName().equals("ADMIN")) {
                session.setAttribute("error", "You do not have permission to access this page");
                modelAndView.setViewName("redirect:/dashboard");
                return modelAndView;
            }
        } else {
            return new ModelAndView("redirect:/login");
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Role> settingList = null;
        session.setAttribute("name", name);
        if (name != null && (speciality == null || speciality.isEmpty()) && (status == null || status.isEmpty())) {
            settingList = roleService.findAllByNameContaining(name, column, order, pageable);
        } else if (name != null && speciality != null && !speciality.isEmpty() && (status == null || status.isEmpty())) {
            settingList = roleService.findAllByNameContainingAndSpeciality(pageable, name, speciality, column, order);
        } else if (name != null && (speciality == null || speciality.isEmpty()) && status != null && !status.isEmpty()) {
            boolean status2 = Boolean.parseBoolean(status);
            settingList = roleService.findAllByNameContainingAndStatus(pageable, name, status2, column, order);
        } else if (name != null && speciality != null && !speciality.isEmpty() && status != null && !status.isEmpty()) {
            boolean status2 = Boolean.parseBoolean(status);
            settingList = roleService.findAllByNameContainingAndStatusAndSpeciality(pageable, name, status2, speciality, column, order);
        } else {
            settingList = roleService.findAll(pageable);
        }
        Set<String> temp = new HashSet<>();
        for (Role role : roleService.findAll()) {
            temp.add(role.getSpeciality());
        }
        Set<String> specialitySet = new HashSet<>(temp);
        modelAndView.setViewName("setting");
        modelAndView.addObject("specialitySet", specialitySet);
        modelAndView.addObject("settingList", settingList.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", settingList.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/setting/adding")
    public ModelAndView settingAdd(Principal principal) {
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
            if (!user.getRole().getName().equals("ADMIN")){
                return new ModelAndView("redirect:/dashboard");
            }
        } else {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("setting-adding");
    }

    @RequestMapping("/setting/adding")
    public String settingAdd2(HttpSession session, Model model,
                              @RequestParam(value = "status", required = false) Boolean status,
                              @RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "speciality", required = false) String speciality,
                              @RequestParam(value = "description", required = false) String description,
                              @RequestParam(value = "priority", required = false) Long priority ,Principal principal) {

        session.setAttribute("name", name);
        session.setAttribute("speciality", speciality);
        session.setAttribute("description", description);
        session.setAttribute("priority", priority);
        session.setAttribute("status", status);
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
            if (!user.getRole().getName().equals("ADMIN")){
                session.setAttribute("error", "You do not have permission to access this page");
                return "redirect:/dashboard";
            }
        } else {
            return "redirect:/login";
        }
        boolean hasError = false;

        if (name == null || name.isEmpty()) {
            model.addAttribute("errorName", "Name cannot be null");
            hasError = true;
        } else if (name.length() > 20) {
            model.addAttribute("errorName", "Name cannot exceed 20 characters");
            hasError = true;
        }

        if (speciality == null || speciality.isEmpty()) {
            model.addAttribute("errorSpeciality", "Speciality cannot be null");
            hasError = true;
        } else if (speciality.length() > 20) {
            model.addAttribute("errorSpeciality", "Speciality cannot exceed 20 characters");
            hasError = true;
        }  else if(speciality.equals("Semester Speciality")) {
            String lastTwoChars = name.length() >= 2 ? name.substring(name.length() - 2) : "";
            if (!lastTwoChars.chars().allMatch(Character::isDigit)) {
                // Last two characters are digits
                model.addAttribute("errorName", "Semester name end with two digits");
                hasError = true;
            }
            else if (name.contains(" ")) {
                model.addAttribute("errorName", "Name cannot contain spaces");
                hasError = true;
            }
        }

        if (priority == null || priority <= 0) {
            model.addAttribute("errorPriority", "Priority must be a positive number");
            hasError = true;
        }

        if (description == null || description.isEmpty()) {
            model.addAttribute("errorDescription", "Description cannot be null");
            hasError = true;
        }

        if (hasError) {
            return "setting-adding"; // The view name of your JSP page
        }

        try {
            roleService.save(name.trim(), description.trim(), speciality.trim(), status, priority);
            return "redirect:/setting";
        } catch (DataIntegrityViolationException | SaveAccountInvalidException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Duplicate entry for Name");
            return "setting-adding";
        }
    }



    @GetMapping("/setting/{id}")
    public ModelAndView settingDetail(@PathVariable Long id, Principal principal, HttpSession session) {
        Role role = roleService.getRoleById(id).orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));
        Set<String> temp = new HashSet<>();
        for (Role a : roleService.findAll()) {
            temp.add(a.getSpeciality());
        }
        Set<String> specialitySet = new HashSet<>(temp);
        specialitySet.remove(role.getSpeciality());
        ModelAndView modelAndView = new ModelAndView();
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
            if ( !user.getRole().getName().equals("ADMIN")) {
                session.setAttribute("error", "You do not have permission to access this page");
                modelAndView.setViewName("redirect:/dashboard");
                return modelAndView;
            }
        } else {
            return new ModelAndView("redirect:/login");
        }
        modelAndView.setViewName("setting-detail");
        modelAndView.addObject("role", role);
        modelAndView.addObject("specialitySet", specialitySet);
        return modelAndView;
    }

    @RequestMapping("/setting/edit/{id}")
    public String editSetting( Model model, HttpSession session, @PathVariable Long id, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "speciality", required = false) String speciality, @RequestParam(value = "description", required = false) String description, @RequestParam(value = "status", required = false) boolean status, @RequestParam(value = "priority", required = false) Long priority) {
        session.setAttribute("name", name);
        session.setAttribute("speciality", speciality);
        session.setAttribute("priority", priority);
        session.setAttribute("description", description);
        try {
            boolean hasError = false;
            if (name == null || name.isEmpty()) {
                model.addAttribute("errorName", "Name cannot be null");
                hasError = true;
            } else if (name.length() > 20) {
                model.addAttribute("errorName", "Name cannot exceed 20 characters");
                hasError = true;
            }

            if (speciality == null || speciality.isEmpty()) {
                model.addAttribute("errorSpeciality", "Speciality cannot be null");
                hasError = true;
            } else if (speciality.length() > 20) {
                model.addAttribute("errorSpeciality", "Speciality cannot exceed 20 characters");
                hasError = true;
            }

            if (priority == null || priority <= 0) {
                model.addAttribute("errorPriority", "Priority must be a positive number");
                hasError = true;
            }

            if (description == null || description.isEmpty()) {
                model.addAttribute("errorDescription", "Description cannot be null");
                hasError = true;
            }

            if (hasError) {
                return "setting-detail"; // The view name of your JSP page
            }
            roleService.updateUsingSaveMethod(id, name.trim(), speciality.trim(), description.trim(), status, priority);
            return "redirect:/setting";
        } catch (DataIntegrityViolationException | SaveAccountInvalidException e) {
            Role role = roleService.getRoleById(id).orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));
//            Set<String> temp = new HashSet<>();
//            for (Role a : roleService.findAll()) {
//                temp.add(a.getSpeciality());
//            }
//            Set<String> specialitySet = new HashSet<>(temp);
//            specialitySet.remove(role.getSpeciality());
            model.addAttribute("errorMessage", "Duplicate entry for Name");
            model.addAttribute("role", role);
//            model.addAttribute("specialitySet", specialitySet);
            return "setting-detail";
        }
    }

    @RequestMapping("/setting/edit2/{id}/page={currentPage}/size={totalPages}")
    public String editStatusDirect(@PathVariable Long id, @PathVariable int currentPage, @PathVariable int totalPages) {
        Role role = roleService.getRoleById(id).orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));
        roleService.updateByStatusId(id, !role.isStatus());
        return "redirect:/setting?page=" + currentPage + "&size=" + 9;
    }

}
