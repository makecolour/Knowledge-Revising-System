package com.krs.knowledgerevisingsystem.controller;

import com.krs.knowledgerevisingsystem.entity.Subject;
import com.krs.knowledgerevisingsystem.entity.User;
import com.krs.knowledgerevisingsystem.service.SubjectService;
import com.krs.knowledgerevisingsystem.service.UserListService;
import com.krs.knowledgerevisingsystem.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserListController {
    private final UserListService userListService;

    public UserListController(UserListService userListService) {
        this.userListService = userListService;
    }

    @GetMapping("/user")
    public ModelAndView user(@RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> user;

        if (keyword != null && !keyword.isEmpty()) {
            user = userListService.findByFullNameContaining(keyword, pageable);
        } else {
            user = userListService.findAll(pageable);
        }

        ModelAndView modelAndView = new ModelAndView("user-list");
        modelAndView.addObject("user", user.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", user.getTotalPages());
        modelAndView.addObject("keyword", keyword);
        return modelAndView;
    }
}
