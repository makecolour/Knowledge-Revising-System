package com.krs.knowledgerevisingsystem.controller;

import com.krs.knowledgerevisingsystem.entity.Role;
import com.krs.knowledgerevisingsystem.entity.StudySet;
import com.krs.knowledgerevisingsystem.entity.Subject;
import com.krs.knowledgerevisingsystem.entity.User;
import com.krs.knowledgerevisingsystem.service.LessonService;
import com.krs.knowledgerevisingsystem.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class StudySetController {
    private LessonService lessonService;
    private UserService userService;

    private StudySetController(LessonService lessonService, UserService userService) {
        this.userService = userService;
        this.lessonService = lessonService;
    }

    @GetMapping("/flash-cards/{id}")
    public ModelAndView flashCards(@PathVariable String id, Principal principal) {
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
        } else {
            return new ModelAndView("redirect:/login");
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("flash-cards");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userService.findByUser(currentUserName);
            modelAndView.addObject("user", user);
        }

        modelAndView.addObject("list", lessonService.findAllStudySet(id));
        modelAndView.addObject("lessonsId",id);
        return modelAndView;
    }

    @PostMapping("/study-set/add")
    public ResponseEntity<String> addStudySet(@RequestParam String term,
                                              @RequestParam String definition,
                                              @RequestParam boolean status,
                                              @RequestParam Long lessonId) {

        boolean isAdded = lessonService.addStudySet(term, definition, status, lessonId);
        if (isAdded) {
            return ResponseEntity.ok("StudySet added successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to add StudySet.");
        }
    }

    @PostMapping("/study-set/update")
    public ResponseEntity<String> updateStudySet(@RequestParam Long id,
                                                 @RequestParam String term,
                                                 @RequestParam String definition,
                                                 @RequestParam boolean status) {

        boolean isUpdated = lessonService.updateStudySet(id, term, definition, status);
        if (isUpdated) {
            return ResponseEntity.ok("StudySet updated successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to update StudySet.");
        }
    }

    @PostMapping("/study-set/delete")
    public ResponseEntity<String> deleteStudySet(@RequestParam Long id) {

        boolean isDeleted = lessonService.deleteStudySet(id);
        if (isDeleted) {
            return ResponseEntity.ok("StudySet deleted successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to delete StudySet.");
        }
    }

    @GetMapping("/study-set/{id}/flash")
    public ModelAndView studySetFlash(Principal principal, @PathVariable String id, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "1") int size) {
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
        } else {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("flash");
        Page<StudySet> studySet = lessonService.findStudySetByLessonId(size, page, Long.parseLong(id));

        modelAndView.addObject("mode", "flash");

        modelAndView.addObject("ssContent", studySet.getContent());
        modelAndView.addObject("id", id);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", studySet.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/study-set/{id}/test")
    public ModelAndView studySetTest(Principal principal, @PathVariable String id, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "1") int size) {
        if (principal != null) {
            User user = userService.findByUser(principal.getName());
        } else {
            return new ModelAndView("redirect:/login");
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("test");
        Page<StudySet> correctStudySetPage = lessonService.findStudySetByLessonId(size, page, Long.parseLong(id));

        modelAndView.addObject("mode", "test");
        // Ensure there is at least one study set to display
        if (correctStudySetPage.hasContent()) {
            StudySet correctStudySet = correctStudySetPage.getContent().get(0);

            // Fetch all study sets for the given lesson ID, excluding the current study set
            List<StudySet> allStudySets = lessonService.findAllStudySet(id);
            allStudySets.removeIf(studySet -> studySet.getId().equals(correctStudySet.getId()));

            // Shuffle and select 3 study sets for false definitions
            Collections.shuffle(allStudySets);
            List<String> falseDefinitions = allStudySets.stream()
                    .limit(3)
                    .map(StudySet::getDefinition)
                    .collect(Collectors.toList());

            // Add the correct study set and false definitions to the model
            modelAndView.addObject("correctStudySet", correctStudySet);
            modelAndView.addObject("falseDefinitions", falseDefinitions);
        } else {
            // Handle case where no study set is found
            modelAndView.addObject("message", "No study set found for the test.");
        }

        modelAndView.addObject("id", id);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", correctStudySetPage.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/study-set/{id}/match")
    public ModelAndView match (Principal principal, @PathVariable String id){
        if (principal != null) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("match");
            modelAndView.addObject("mode", "match");
            modelAndView.addObject("list", lessonService.findAllStudySet(id));
            modelAndView.addObject("lessonsId",id);
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/login");
        }


    }
}
