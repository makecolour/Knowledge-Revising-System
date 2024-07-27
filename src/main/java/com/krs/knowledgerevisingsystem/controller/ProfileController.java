package com.krs.knowledgerevisingsystem.controller;

import com.krs.knowledgerevisingsystem.entity.Course;
import com.krs.knowledgerevisingsystem.entity.User;
import com.krs.knowledgerevisingsystem.entity.UserCode;
import com.krs.knowledgerevisingsystem.service.CourseService;
import com.krs.knowledgerevisingsystem.service.EmailService;
import com.krs.knowledgerevisingsystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
public class ProfileController {
    private final UserService userService;
    private final EmailService emailService;
    private final CourseService courseService;

    public ProfileController(UserService userService, EmailService emailService, CourseService courseService) {
        this.emailService = emailService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @RequestMapping("/profile")
    public ModelAndView profile(Principal principal) {
        User user = userService.findByUser(principal.getName());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        modelAndView.addObject("user", user);
        modelAndView.addObject("currentUser", user);
        return modelAndView;
    }

    @GetMapping("/change-password")
    public ModelAndView changePassword(Principal principal) {
        User user = userService.findByUser(principal.getName());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("change-password");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/get-verify-code")
    public ResponseEntity<?> getVerificationCode(@RequestParam String email, HttpSession session) {
        try {
            UserCode userCode = userService.saveUserCode(userService.findUserByEmail(email), userService.generateCode());
//            emailService.mailGunAPI("sandbox9f32a7a399644a1b867b99a2a30f569c.mailgun.org",
//                    "80bd890118d35ae9431b5344e270124d-a2dd40a3-3fe8d99f",
//                    "KRS Reset Password <krs_resetpassword@sandbox9f32a7a399644a1b867b99a2a30f569c.mailgun.org>",
//                    email, "Reset password", "Your verification code is: " + userCode.getCode());
            emailService.sendSMTP("Knowledge Revising System", email, "Reset password", "Your verification code is: " + userCode.getCode());
            return ResponseEntity.ok("Verification code sent successfully.");
        } catch (Exception e) {
            // Log the error for debugging purposes
            // Consider using a logger to log the exception details
            return ResponseEntity.status(503).body("Error when processing your request: " + e.getMessage());
        }
    }

    @RequestMapping("/profile/{userId}")
    public ModelAndView profile(@PathVariable Long userId, Principal principal) {
        User user = userService.findById(userId);
        User currentUser = userService.findByUser(principal.getName());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        if(user.getRole().getName().equalsIgnoreCase("TEACHER"))
        {
            List<Course> list = courseService.getAllCourseByTeacher(user);
            modelAndView.addObject("listCourse", list);
        }
        modelAndView.addObject("user", user);
        modelAndView.addObject("currentUser", currentUser);
        return modelAndView;
    }

//    @PostMapping("/profile/{userId}/password")
//    public ModelAndView changePassword(@PathVariable Long userId, @RequestParam String oldPassword, @RequestParam String password, @RequestParam String retypePassword, Principal principal) {
//        ModelAndView mav = new ModelAndView("change-password");
//        User user = userService.findById(userId);
//        User currentUser = userService.findByUser(principal.getName());
//        if (!currentUser.equals(user)) {
//            mav.addObject("passwordError", "You are not allowed to change password of other user");
//            return mav;
//        }
//        else if (!userService.checkPassword(user, oldPassword)) {
//            mav.addObject("passwordError", "Old password is incorrect");
//            return mav;
//        }
//        else if (!password.equals(retypePassword)) {
//            mav.addObject("passwordError", "New password and retype password are not the same");
//            return mav;
//        }
//        userService.changePassword(user,password);
//
//        mav.addObject("passwordSuccess", "Password changed successfully");
//        return mav;
//    }

    @PostMapping("/profile/{userId}/password")
    public ModelAndView changePassword(@PathVariable Long userId,
                                       @RequestParam String verification,
                                       @RequestParam String oldPassword,
                                       @RequestParam String password,
                                       @RequestParam String retypePassword,
                                       Principal principal) {
        ModelAndView mav = new ModelAndView("change-password");
        User user = userService.findById(userId);
        User currentUser = userService.findByUser(principal.getName());

        mav.addObject("currentUser", currentUser);
        mav.addObject("user", user);

        if (!currentUser.equals(user)) {
            mav.addObject("passwordError", "You are not allowed to change password of other user");
            return mav;
        }

        // Check if the verification code matches
        if (!userService.checkUserCode(Integer.parseInt(verification), user.getId())) {
            mav.addObject("passwordError", "Verification code is incorrect");
            return mav;
        }

        if (!userService.checkPassword(user, oldPassword)) {
            mav.addObject("passwordError", "Old password is incorrect");
            return mav;
        }

        if (!password.equals(retypePassword)) {
            mav.addObject("passwordError", "New password and retype password are not the same");
            return mav;
        }

        userService.changePassword(user, password);
        mav.addObject("passwordSuccess", "Password changed successfully");
        return mav;
    }

    @PostMapping("/profile/{userId}/details")
    public ModelAndView changeProfile(@PathVariable Long userId, Principal principal, @RequestParam String name, @RequestParam String note) {
        ModelAndView mav = new ModelAndView("redirect:/profile");
        User user = userService.findById(userId);
        User currentUser = userService.findByUser(principal.getName());
        if (!user.equals(currentUser)) {
            mav.addObject("detailError", "You are not allowed to change profile of other user");
            return mav;
        }
        // Change profile
        userService.changeProfile(user, name, note);
        mav.addObject("detailSuccess", "Details changed successfully");
        return mav;
    }

    @PostMapping("/profile/{userId}/delete")
    public ModelAndView deleteProfile(@PathVariable Long userId, Principal principal, HttpSession session) {
        ModelAndView mav = new ModelAndView("redirect:/profile");
        User user = userService.findById(userId);
        User currentUser = userService.findByUser(principal.getName());
        if (user.equals(currentUser)) {
            userService.deleteUser(user);
            mav.addObject("deleteMessage", "Profile deleted successfully");
            session.invalidate();
            return mav;
        } else if (currentUser.getRole().getName().toUpperCase().contains("ADMIN")) {
            userService.banUser(user);
            mav.addObject("deleteMessage", "Profile banned successfully");
            return mav;
        }
        else {
            mav.addObject("deleteError", "You are not allowed to delete other user");
            return mav;
        }
        // Delete profile

    }

    @PostMapping("/profile/{userId}/deactivate")
    public ModelAndView deactivateProfile(@PathVariable Long userId, Principal principal, HttpSession session) {
        ModelAndView mav = new ModelAndView("redirect:/profile");
        User user = userService.findById(userId);
        User currentUser = userService.findByUser(principal.getName());
        if (user.equals(currentUser)) {
            userService.banUser(user);
            mav.addObject("deactivateMessage", "Profile deactivated successfully");
            session.invalidate();
            return mav;
        } else if (currentUser.getRole().getName().toUpperCase().contains("ADMIN")) {
            userService.banUser(user);
            mav.addObject("deactivateMessage", "Profile deactivated successfully");
            return mav;
        } else {
            mav.addObject("deactivateError", "You are not allowed to deactivate other user");
            return mav;
        }
    }
        // Deactivate profile






//    @PostMapping("/profile/{userId}/avatar")
//    public ModelAndView uploadAvatar(@RequestParam("file") MultipartFile file, Principal principal) {
//        try {
//            byte[] imageBytes = file.getBytes();
//            String username = principal.getName();
//            User user = userService.findByUser(username);
//            user.setAvatar(imageBytes);
//            userService.saveUser(user);
//            // Redirect or return view
//        } catch (IOException e) {
//            // Handle error
//        }
//    }

}
