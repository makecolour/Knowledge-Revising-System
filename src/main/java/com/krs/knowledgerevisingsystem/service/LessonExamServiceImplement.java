package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.entity.LessonExam;
import com.krs.knowledgerevisingsystem.repository.LessonExamRepository;
import com.krs.knowledgerevisingsystem.repository.LessonRepository;
import com.krs.knowledgerevisingsystem.repository.QuestionRepository;
import com.krs.knowledgerevisingsystem.repository.StudySetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonExamServiceImplement implements LessonExamService{

    private final LessonRepository lessonRepository;

    private final QuestionRepository questionRepository;

    private final StudySetRepository studySetRepository;

    private final LessonExamRepository lessonExamRepository;

    public LessonExamServiceImplement(LessonExamRepository lessonExamRepository,LessonRepository lessonRepository, QuestionRepository questionRepository, StudySetRepository studySetRepository) {
        this.lessonRepository = lessonRepository;
        this.questionRepository = questionRepository;
        this.studySetRepository = studySetRepository;
        this.lessonExamRepository = lessonExamRepository;
    }
    @Override
    public LessonExam findById(Long id) {
        LessonExam lessonExam = lessonExamRepository.findById(id).orElse(null);
        if(lessonExam != null){
            return lessonExam;
        }
        return null;
    }

    @Override
    public void saveLessonExam(LessonExam lessonExam) {

    }

    @Override
    public List<LessonExam> findAllByExamId(Long examId) {
        return lessonExamRepository.findAllByExamId(examId);
    }

    @Override
    public void updateLessonExam(LessonExam lessonExam) {
        lessonExamRepository.save(lessonExam);
    }
}
