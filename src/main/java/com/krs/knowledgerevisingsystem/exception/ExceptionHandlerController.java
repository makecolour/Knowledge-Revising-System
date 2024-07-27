package com.krs.knowledgerevisingsystem.exception;

import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(SaveAccountInvalidException.class)
    public String handleDateException(SaveAccountInvalidException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/register";
    }
}
