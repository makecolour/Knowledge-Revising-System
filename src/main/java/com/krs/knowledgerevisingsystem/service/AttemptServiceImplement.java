package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.dto.ExamAttemptCount;
import com.krs.knowledgerevisingsystem.entity.*;
import com.krs.knowledgerevisingsystem.exception.ManageExam.AttemptInvalidException;
import com.krs.knowledgerevisingsystem.repository.AnswerRepository;
import com.krs.knowledgerevisingsystem.repository.AttemptHistoryAnswerRepository;
import com.krs.knowledgerevisingsystem.repository.AttemptHistoryRepository;
import com.krs.knowledgerevisingsystem.repository.AttemptRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttemptServiceImplement implements AttemptService{
    private final AttemptRepository attemptRepository;
    private final AttemptHistoryRepository attemptHistoryRepository;
    private final AnswerRepository answerRepository;
    private final AttemptHistoryAnswerRepository attemptHistoryAnswerRepository;

    public AttemptServiceImplement(AttemptRepository attemptRepository, AttemptHistoryRepository attemptHistoryRepository, AnswerRepository answerRepository, AttemptHistoryAnswerRepository attemptHistoryAnswerRepository) {
        this.attemptRepository = attemptRepository;
        this.attemptHistoryRepository = attemptHistoryRepository;
        this.answerRepository = answerRepository;
        this.attemptHistoryAnswerRepository = attemptHistoryAnswerRepository;
    }

    @Override
    public List<Attempt> findAll() {
        return attemptRepository.findAll();
    }

    @Override
    public List<Attempt> findByUserAndExam(Long studentId, Long examId) {
        return attemptRepository.findAllByUserIdAndExamId(studentId, examId);
    }

    @Override
    public List<AttemptHistory> getAttemptHistoryByAttemptId(Long attemptId){
        return attemptHistoryRepository.findAllByAttemptId(attemptId);
    }

    @Override
    public Attempt findByAttemptId(Long attemptId) throws AttemptInvalidException {
               Optional<Attempt> attempt= attemptRepository.findById(attemptId);
               if(attempt.isPresent()){
                   return attempt.get();
               }
               else{
                   throw new AttemptInvalidException("Attempt not found");
               }
    }

    @Override
    public Answer findAnswerById(Long answerId) {
        return answerRepository.findById(answerId).get();
    }

    @Transactional
    @Override
    public boolean saveAttempt(Attempt attempt) {
        return attemptRepository.save(attempt) != null;
    }

    @Transactional
    @Override
    public List<AttemptHistory> generateAttemptHistory(Attempt attempt, List<Question> questions) {
        List<AttemptHistory> attemptHistories = new ArrayList<>();
        for (Question question : questions) {
            AttemptHistory attemptHistory = new AttemptHistory();
            attemptHistory.setAttempt(attempt);
            attemptHistory.setQuestion(question);
            attemptHistories.add(attemptHistory);
            System.out.println(question.getId());
        }
        attemptHistoryRepository.saveAll(attemptHistories);
        return attemptHistories;
    }

    @Transactional
    @Override
    public void saveAttemptHistoryAnswers(List<AttemptHistoryAnswer> attemptHistoriyAnswers) {
        attemptHistoryAnswerRepository.saveAll(attemptHistoriyAnswers);
    }

    @Transactional
    @Override
    public Boolean saveAttemptHistoryAnswer(AttemptHistoryAnswer attemptHistoryAnswer) {
        attemptHistoryAnswerRepository.save(attemptHistoryAnswer);
        return true;
    }


    @Override
    public List<Answer> getCorrectAnswerOfQuestion(Long questionId) {
        List<Answer> answers = answerRepository.findAllByQuestionId(questionId);
        List<Answer> correctAnswers = new ArrayList<>();
        for (Answer answer : answers) {
            if (answer.isCorrect()) {
                correctAnswers.add(answer);
            }
        }
        return correctAnswers;
    }

    @Override
    public Boolean compareAnswers(List<String> answers, List<Answer> correctAnswers) {
        return null;
    }

    @Scheduled(fixedRate = 20000000)
    @Override
    public void updateExpiredAttempts() {
        List<Attempt> allAttempts = attemptRepository.findAllAttempts();
        List<Attempt> expiredAttempts = new ArrayList<>();

        for (Attempt attempt : allAttempts) {
            // Convert the exam length to milliseconds
            long examLengthMillis = attempt.getExam().getLength()*60000;
            // Check if the current time is greater than the attempt date plus the exam length
            if (System.currentTimeMillis() > (attempt.getAttemptDate().getTime() + examLengthMillis)) {
                expiredAttempts.add(attempt);
            }
        }

        for (Attempt attempt : expiredAttempts) {
            attempt.setStatus(false);  // Set status to fail
        }
        attemptRepository.saveAll(expiredAttempts);  // Save the updated attempts
    }

    @Override
    public List<Attempt> findByUserExam(Exam exam, User user) {
        return attemptRepository.findAllByExamAndUser(exam, user);
    }

    @Override
    public List<Answer> convertStringToAnswer(String[] answerIds) throws AttemptInvalidException {
        List<Answer> answers = new ArrayList<>();
        for(String answerId : answerIds){
            try {
                Optional<Answer> answer = answerRepository.findById(Long.parseLong(answerId));
                answers.add(answer.get());
            } catch (Exception e) {
                // Handle the exception
                throw new AttemptInvalidException("Invalid answer ID");
            }
        }
        return answers;
    }

    public Answer convertStringToAnswer(String answer) throws AttemptInvalidException {
        try {
            Optional<Answer> answerOptional = answerRepository.findById(Long.parseLong(answer));
            return answerOptional.get();
        } catch (Exception e) {
            // Handle the exception
            throw new AttemptInvalidException("Invalid answer ID");
        }
    }

    @Override
    public Optional<ExamAttemptCount> findExamWithMostAttempts() {
        List<Object[]> results = attemptRepository.findExamWithMostAttempts();
        if (!results.isEmpty()) {
            Object[] row = results.get(0);
            String examName = (String) row[0];
            Long count = ((Number) row[1]).longValue();
            return Optional.of(new ExamAttemptCount(count, examName));
        }
        return Optional.empty();
    }

}

