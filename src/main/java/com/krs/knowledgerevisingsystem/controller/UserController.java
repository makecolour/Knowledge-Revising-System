package com.krs.knowledgerevisingsystem.controller;

import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import com.krs.knowledgerevisingsystem.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/create-user")
    public ModelAndView createUser() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new-user");
        modelAndView.addObject("listRole", userService.listRole());
        return modelAndView;
    }

    @PostMapping("/create-user")
    public ModelAndView createUser(@RequestParam String name ,@RequestParam String role ,@RequestParam String email) {
        userService.createUser(name, role, email);
        return new ModelAndView("redirect:/create-user");
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public ModelAndView register(@RequestParam String password, @RequestParam String name, @RequestParam String email, @RequestParam String role) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            userService.save(name, password, email, role);
            modelAndView.setViewName("login");
        } catch (SaveAccountInvalidException e) {
            modelAndView.addObject("errorMessage", e.getMessage());
            modelAndView.setViewName("register");
        }
        return modelAndView;
    }


}
