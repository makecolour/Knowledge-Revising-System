package com.krs.knowledgerevisingsystem.service;


import com.krs.knowledgerevisingsystem.dto.LessonsDto;
import com.krs.knowledgerevisingsystem.entity.*;
import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LessonService {
    @Transactional
    Boolean save(String title, Subject subject, int order,boolean status) throws SaveAccountInvalidException;
    @Transactional
    Boolean saveAnswer(Long id, String content, boolean isCorrect, Question question) throws SaveAccountInvalidException;
    @Transactional
    Boolean addAnswer(String content, boolean isCorrect, Question question) throws SaveAccountInvalidException;

    List<Lesson> getAll();

    List<Lesson> getAllLessonSameSubject(Long subjectId);

    Page<LessonsDto> findLessonByAll(int size, int page, String id, String keyword);

    Page<Question> findQuestionByAll(int size, int page, String id, String keyword);

    Page<StudySet> findStudySetByAll(int size, int page, String id, String keyword);

    Boolean deleteQuestion(String id);

    Boolean editQuestion(Long id, String content, short level, boolean status);

    Boolean addQuestion(String content, short level, boolean status, Long lessonsId);

    Question getQuestionByContent(String content);

    Optional<Question> findQuestionById(Long id);

    List<Question> getAllQuestion();

    List<Question> getAllQuestionByStatus(boolean status);

    Answer findAnswerWithId(Long id);

    @Transactional
    Question findQuestionByIdNonOp(Long id);

    List<StudySet> findAllStudySet(String id);

    Boolean addStudySet(String term, String definition, boolean status, Long lessonId);

    Boolean updateStudySet(Long id, String term, String definition, boolean status);

    Boolean deleteStudySet(Long id);

    void updateByStatusId(Long id, boolean b);

    @Transactional
    List<Answer> findAnswerById(Long id);

    @Transactional
    List<Answer> findAnswerByQuestionId(Long id);

    @Transactional
    Question getQuestionByContentAndLesson(String content, Long lessonId);

    @Transactional
    Lesson findLessonById(Long id);

    void deleteAnswer(Answer a);

    void updateLesson(Long id, String title, boolean status, Long subjectId, int order) throws DataIntegrityViolationException;

    Lesson findLessonBySubject_Id(Long id);

    Page<StudySet> findStudySetByLessonId(int size, int page, Long id);
    Page<Lesson> findAllBySubject_Id(Long id, int size,Pageable pageable);

    List<StudySet> findAllStudySet();
    Page<Lesson> getAll(Pageable pageable);

    Page<Lesson> findAllByNameContaining(String name, Pageable pageable, String column, String direction);

    Page<Lesson> findAllByNameContainingAndStatus(Pageable pageable, String name, boolean status, String column, String direction);

    Page<Lesson> findAllByNameContainingAndStatusAndSubjectId(Pageable pageable, String name, boolean status, Long subjectId, String column, String direction);

    Page<Lesson> findAllByNameContainingAndSubjectId(Pageable pageable, String name, Long subjectId, String column, String direction);


}
