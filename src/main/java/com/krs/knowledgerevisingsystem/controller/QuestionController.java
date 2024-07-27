package com.krs.knowledgerevisingsystem.controller;

import com.krs.knowledgerevisingsystem.dto.LessonsDto;
import com.krs.knowledgerevisingsystem.entity.*;
import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import com.krs.knowledgerevisingsystem.service.LessonService;
import com.krs.knowledgerevisingsystem.service.SubjectService;
import com.krs.knowledgerevisingsystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class QuestionController {
    @Value("${default.size}")
    private Integer defaultSize;
    private SubjectService subjectService;
    private UserService userService;
    private LessonService lessonService;

    public QuestionController(SubjectService subjectService, LessonService lessonService, UserService userService) {
        this.subjectService = subjectService;
        this.lessonService = lessonService;
        this.userService = userService;
    }

    @GetMapping("/lessons-list/{id}")
    public ModelAndView lessonsList(Principal principal, @PathVariable String id,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "5") int size,
                                    @RequestParam(defaultValue = "") String keyword) {

        if (principal == null) {
            return new ModelAndView("redirect:/login");
        }

        User user = userService.findByUser(principal.getName());
        ModelAndView modelAndView = new ModelAndView();
        Page<LessonsDto> lessonPage = lessonService.findLessonByAll(size, page, id, keyword);

        modelAndView.setViewName("lessons-list");
        modelAndView.addObject("user", user);
        modelAndView.addObject("listlessons", lessonPage.getContent());
        modelAndView.addObject("totalPages", lessonPage.getTotalPages());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("keyword", keyword);
        modelAndView.addObject("id", id);
        return modelAndView;
    }


    @GetMapping("/question-list/{id}")
    public ModelAndView questionList(Principal principal, @PathVariable String id,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int size,
                                     @RequestParam(defaultValue = "") String keyword,
                                     @RequestParam(value = "message", required = false) String message) {

        if (principal == null) {
            return new ModelAndView("redirect:/login");
        }

        Page<Question> questionPage = lessonService.findQuestionByAll(size, page, id, keyword);
        ModelAndView modelAndView = new ModelAndView("question-list");
        if (message != null) {
            modelAndView.addObject("message", message);
        }
        User user = userService.findByUser(principal.getName());
        modelAndView.addObject("question", questionPage.getContent());
        modelAndView.addObject("user", user);
        modelAndView.addObject("totalPages", questionPage.getTotalPages());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("keyword", keyword);
        modelAndView.addObject("lessonsId",id);
        return modelAndView;
    }

    @PostMapping("/question/delete")
    @ResponseBody
    public ResponseEntity<String> deleteQuestion(@RequestParam String ids, @RequestParam String questionId) {
        boolean isDeleted = lessonService.deleteQuestion(ids);
        if (isDeleted) {
            return ResponseEntity.ok("Question deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the question.");
        }
    }

    @PostMapping("/question/add")
    @ResponseBody
    public ResponseEntity<String> addQuestion(@RequestParam String content,
                                              @RequestParam short level,
                                              @RequestParam boolean status,
                                              @RequestParam Long lessonsId) {
        boolean isAdded = lessonService.addQuestion(content, level, status, lessonsId);
        if (isAdded) {
            Question q = lessonService.getQuestionByContentAndLesson(content, lessonsId);
            return ResponseEntity.ok(q.getId().toString());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add the question.");
        }
    }

    @GetMapping("/question/add/{questionId}")
    public ModelAndView answerAddQuestion(Principal principal, @PathVariable String questionId) {
        if (principal == null) {
            return new ModelAndView("redirect:/login");
        }
        User user = userService.findByUser(principal.getName());
        Question question = lessonService.findQuestionByIdNonOp(Long.valueOf(questionId));
        ModelAndView modelAndView = new ModelAndView("question");
        modelAndView.addObject("user", user);
        modelAndView.addObject("question", question);
        return modelAndView;
    }

    @GetMapping("/question/edit/{questionId}")
    public ModelAndView answerEditQuestion(Principal principal, @PathVariable String questionId) {
        if (principal == null) {
            return new ModelAndView("redirect:/login");
        }
        User user = userService.findByUser(principal.getName());
        List<Answer> answerList = lessonService.findAnswerById(Long.valueOf(questionId));
        Question question = lessonService.findQuestionByIdNonOp(Long.valueOf(questionId));
        ModelAndView modelAndView = new ModelAndView("question-edit");
        modelAndView.addObject("user", user);
        modelAndView.addObject("question", question);
        modelAndView.addObject("questionList", answerList);
        return modelAndView;
    }

    public String isValid(String name, Long answerId) {
        if (name == null || name.isEmpty()) {
            return "Lesson name must not be empty";
        } else if (name.length() > 20) {
            return "Lesson name must not be more than 20 characters";
        }
        for (Answer a : lessonService.findAnswerByQuestionId(answerId)) {
            if (a.getContent().equals(name)) {
                return "Lesson name must be unique";
            }
        }
        return "valid";
    }

    @PostMapping("/question/submit-edit/{questionId}")
    public ModelAndView answerSubmitEditQuestion(Principal principal,
                                              @PathVariable String questionId,
                                              @RequestParam(value = "answerBox", required = false) List<String> answer,
                                              @RequestParam(value = "correctNess", required = false) String correct,
                                              Model model) throws SaveAccountInvalidException {
        if (principal == null) {
            return new ModelAndView("redirect:/login");
        }

        List<Answer> answerList = lessonService.findAnswerById(Long.valueOf(questionId));
        Question question = lessonService.findQuestionByIdNonOp(Long.valueOf(questionId));

        if (answer == null || answer.isEmpty()) {
            model.addAttribute("errorMessage", "Answer must not be empty.");
            model.addAttribute("question", question);
            model.addAttribute("questionList", answerList);
            return new ModelAndView("question-edit");
        }

        for (int i = 0; i < answer.size(); i++)
        {
            if (answerList.isEmpty() || answerList.size() <= i) {
                lessonService.addAnswer(answer.get(i), correct.equalsIgnoreCase(String.valueOf(i)), question);
            } else{
                if (!answer.get(i).equalsIgnoreCase(String.valueOf(answerList.get(i)))) {
                    lessonService.addAnswer(answer.get(i), correct.equalsIgnoreCase(String.valueOf(i)), question);
                }
            }
        }

        return new ModelAndView("redirect:/question-list/" + questionId);
    }

    @PostMapping("/answer/add")
    @ResponseBody
    public ResponseEntity<String> addAnswer(@RequestParam String content,
                                              @RequestParam(value = "answerId") String qId) throws SaveAccountInvalidException {
        boolean isAdded = lessonService.addAnswer(content, false, lessonService.findQuestionByIdNonOp(Long.parseLong(qId)));
        if (isAdded) {
            return ResponseEntity.ok("Answer added successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add the question.");
        }
    }

    @PostMapping("/answer/correct/{questionId}")
    public String answerCorrect(@PathVariable String questionId, @RequestParam(value = "correctNess") String correctString) throws SaveAccountInvalidException {
        List<Answer> answerList = lessonService.findAnswerByQuestionId(Long.parseLong(questionId));

        System.out.println("Correctness: " + correctString);
        for (int i = 0; i < answerList.size(); i++) {
            boolean bool = false;
            if (answerList.get(i).getId().toString().equals(correctString)) {
                bool = true;
            } else {
                bool = false;
            }
            lessonService.saveAnswer(answerList.get(i).getId(), answerList.get(i).getContent(), bool, answerList.get(i).getQuestion());
        }

        return "redirect:/question/edit/" + questionId;
    }

    @GetMapping("/answer/delete/{answerId}")
    public String answerDelete(@PathVariable String answerId) {
        System.out.println("Answer ID: " + answerId);
        Answer a = lessonService.findAnswerWithId(Long.parseLong(answerId));
        String questionId = a.getQuestion().getId().toString();
        lessonService.deleteAnswer(a);
        return "redirect:/question/edit/" + questionId;
    }
}
