package com.krs.knowledgerevisingsystem.controller;

import com.krs.knowledgerevisingsystem.entity.User;
import com.krs.knowledgerevisingsystem.service.*;
import kong.unirest.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class ContactController {
    private final UserService userService;

    private final EmailService emailService;

    private final ReCaptchaService reCaptchaService;

    private final ContactService contactService;

    public ContactController(UserService userService, EmailService emailService, ReCaptchaService reCaptchaService, ContactService contactService) {
        this.userService = userService;
        this.emailService = emailService;
        this.reCaptchaService = reCaptchaService;
        this.contactService = contactService;
    }

    @GetMapping("/contact")
    public ModelAndView showContactForm() {
        ModelAndView mav = new ModelAndView("contact");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userService.findByUser(currentUserName);
            mav.addObject("user", user);
        }

        List<User> admin = userService.getUserByRole("ADMIN");

        if (!admin.isEmpty()) {
            mav.addObject("combo", admin);
        }

        mav.addObject("siteKey", reCaptchaService.getSiteKey());
        return mav;
    }

    @PostMapping("/contact")
    public ModelAndView contact(@RequestParam(value = "adminList", required = true) Long adminList, @RequestParam(required = true) String name, @RequestParam(required = true) String email, @RequestParam(required = true) String phone, @RequestParam(required = true) String subject, @RequestParam(required = true) String message, @RequestParam(name="g-recaptcha-response") String recaptchaResponse) {
        // Verify the reCAPTCHA response
        ModelAndView mav = new ModelAndView("contact");
        mav.addObject("siteKey", reCaptchaService.getSiteKey());
        if(!reCaptchaService.verifyReCaptcha(recaptchaResponse)) {
            mav.addObject("type", "error");
            mav.addObject("msg", "Invalid reCAPTCHA. Please try again.");
            return mav;
        }
        Optional<User> admin;
        if(adminList == null) {
            mav.addObject("type", "error");
            mav.addObject("msg", "Please select an admin");
            return mav;
        }
        else{
            admin = Optional.ofNullable(userService.findById(adminList));
            if(admin.isEmpty()) {
                mav.addObject("type", "error");
                mav.addObject("msg", "Admin not found");
                return mav;
            }
        }

            try {
//                emailService.mailGunAPI("sandbox9f32a7a399644a1b867b99a2a30f569c.mailgun.org",
//                        "80bd890118d35ae9431b5344e270124d-a2dd40a3-3fe8d99f",
//                        "KRS Contact <krs_contactadmin@sandbox9f32a7a399644a1b867b99a2a30f569c.mailgun.org>",
//                        admin.get().getEmail(),
//                        "Contact from " + name, "Name: " + name + "\nEmail: " + email + "\nPhone: " + phone + "\nSubject: " + subject + "\nMessage: " + message);
                emailService.sendSMTP("KRS Contact", admin.get().getEmail(), "Contact from " + name, "Name: " + name + "\nEmail: " + email + "\nPhone: " + phone + "\nSubject: " + subject + "\nMessage: " + message);
                contactService.saveContact(name, email, phone, message, admin.get());
                mav.addObject("type", "success");
                mav.addObject("msg", "Email sent successfully");
            } catch (Exception e) {
                mav.addObject("type", "error");
                mav.addObject("msg", "Error when sending email: " + e.getMessage());
            }

        return mav;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
        ModelAndView mav = new ModelAndView("contact");
        mav.addObject("type", "error");
        mav.addObject("msg", "You must fill all the fields");
        mav.addObject("siteKey", reCaptchaService.getSiteKey());
        return mav;
    }
}

