package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.dto.ExamAttemptCount;
import com.krs.knowledgerevisingsystem.entity.*;
import com.krs.knowledgerevisingsystem.exception.ManageExam.AttemptInvalidException;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Optional;

public interface AttemptService {
    List<Attempt> findAll();
    List<Attempt> findByUserAndExam(Long studentId, Long examId);
    List<AttemptHistory> getAttemptHistoryByAttemptId(Long attemptId);
    Attempt findByAttemptId(Long attemptId) throws AttemptInvalidException;
    Answer findAnswerById(Long answerId);
    boolean saveAttempt(Attempt attempt);
    @Scheduled(fixedRate = 60000)
    void updateExpiredAttempts();
    List<Attempt> findByUserExam(Exam exam, User user);
    List<Answer> convertStringToAnswer(String[] answers) throws AttemptInvalidException;
    Answer convertStringToAnswer(String answer) throws AttemptInvalidException;
    List<Answer> getCorrectAnswerOfQuestion(Long questionId);
    Boolean compareAnswers(List<String> answers, List<Answer> correctAnswers);
    Optional<ExamAttemptCount> findExamWithMostAttempts();
    List<AttemptHistory> generateAttemptHistory(Attempt attempt, List<Question> questions);
    void saveAttemptHistoryAnswers(List<AttemptHistoryAnswer> attemptHistoriyAnswers);
    Boolean saveAttemptHistoryAnswer(AttemptHistoryAnswer attemptHistoryAnswer);
}
