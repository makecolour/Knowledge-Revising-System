package com.krs.knowledgerevisingsystem.controller;

import com.krs.knowledgerevisingsystem.entity.UserCode;
import com.krs.knowledgerevisingsystem.service.EmailService;
import com.krs.knowledgerevisingsystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class ForgetPasswordController {

    UserService userService;

    EmailService emailService;

    public ForgetPasswordController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/forget-password")
    public ModelAndView resetPassword() {
        ModelAndView mav = new ModelAndView("forget-password");
        return mav;
    }

    @PostMapping("/forget-password/nextstep")
    public ModelAndView postEmail(@RequestParam String email, Model model, HttpSession session){
        ModelAndView redirect = new ModelAndView("forget-password");
        UserCode userCode;
        try{
            userCode = userService.saveUserCode(userService.findUserByEmail(email), userService.generateCode());}
        catch (Exception e){
            redirect.addObject("error", e.getMessage());
            redirect.addObject("email", email);
            return redirect;
        }

        try{
//            emailService.mailGunAPI("sandbox9f32a7a399644a1b867b99a2a30f569c.mailgun.org",
//                    "80bd890118d35ae9431b5344e270124d-a2dd40a3-3fe8d99f",
//                    "KRS Reset Password <krs_resetpassword@sandbox9f32a7a399644a1b867b99a2a30f569c.mailgun.org>",
//                    email, "Reset password", "Your verification code is: " + userCode.getCode());
           emailService.sendSMTP("Knowledge Revising System", email, "Reset password", "Your verification code is: " + userCode.getCode());
        }catch (Exception e){
            redirect.addObject("error", "Error when sending email: " + e.getMessage());
            return redirect;
        }
        session.setAttribute("id", userCode.getUserId());
        ModelAndView mav = new ModelAndView("forget-password-nextstep");
        return mav;
    }

    @PostMapping("/forget-password/laststep")
    public ModelAndView verify(@RequestParam Integer verification, HttpSession session, Model model) {
        ModelAndView redirect = new ModelAndView("forget-password-nextstep");
        Long id = (Long) session.getAttribute("id");
        if(!userService.checkUserCode(verification, id)) {
            redirect.addObject("error", "Verification code is incorrect");
            return redirect;
        }
        UserCode userCode;
        try {
            userCode = userService.saveUserCode(userService.findUserById(id), null);
        }catch (Exception e) {
            redirect.addObject("error", e.getMessage());
            return redirect;
        }
        model.addAttribute("id", id);
        ModelAndView mav = new ModelAndView("forget-password-laststep");
        return mav;
    }
    
    @PostMapping("/success-recover")
    public ModelAndView successRecover(@RequestParam String password, @RequestParam String retypePassword, @RequestParam Long id, Model model) {
        ModelAndView redirect = new ModelAndView("forget-password-laststep");
        if(!retypePassword.equals(password)) {
            redirect.addObject("error", "Password and retype password are not the same");
            return redirect;
        } else if (password.isEmpty()) {
            redirect.addObject("error", "Password is empty");
            return redirect;
        }

        try {
            userService.savePassword(userService.findUserById(id), password);
        }catch (Exception e) {
            redirect.addObject("error", e.getMessage());
            return redirect;
        }
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }

}
