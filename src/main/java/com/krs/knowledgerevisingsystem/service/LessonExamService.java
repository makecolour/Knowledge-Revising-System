package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.entity.LessonExam;

import java.util.List;

public interface LessonExamService {
    LessonExam findById(Long id);

    void saveLessonExam(LessonExam lessonExam);

    List<LessonExam> findAllByExamId(Long examId);

    void updateLessonExam(LessonExam lessonExam);
}
