package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.entity.*;
import com.krs.knowledgerevisingsystem.exception.ManageExam.ExamInvalidException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExamService {
    List<Question> generateQuestions(Exam exam);

    Exam findById(Long examId) throws ExamInvalidException;

    void saveExam(Exam exam);

    Boolean deleteExam(Long examId) throws ExamInvalidException;

    Page<Exam> findAllByCourseIdAndNameContaining(Long courseId, String name, Pageable pageable);

    Page<Exam> findAllByStatusAndNameContaining(boolean status, String name, Pageable pageable);

    Page<Exam> findAllByCourseIdAndStatusAndNameContaining(Long courseId, boolean status, String name, Pageable pageable);

    Page<Exam> findAll(Pageable pageable);

    Page<Exam> findAllExamWithStatus(User creator, boolean status, Pageable pageable);

    Page<Exam> findAllExam(String name, Long creatorId, Long subjectId, Long courseId, Boolean status, Pageable pageable);

    Exam addExam(String name, String description, Long length, Long courseId, boolean status, short course) throws ExamInvalidException;

    Page<Exam> findAllByNameContaining(Pageable pageable, String name);

    void updateUsingSaveMethod(Long id, String name, Long length, short question, String description, boolean status);

    void updateByStatusId(Long id, boolean b);

    List<Exam> getAllExam();

    List<Question> getAllQuestion();

    List<Exam> getAllExamByCourseId(Long courseId);

    Exam addPrivateExam(String name, String description, User creator, Long subjectId, Long length, boolean status, short question);
}
