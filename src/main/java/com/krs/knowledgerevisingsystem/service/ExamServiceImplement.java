package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.entity.*;
import com.krs.knowledgerevisingsystem.exception.ManageExam.ExamInvalidException;
import com.krs.knowledgerevisingsystem.repository.CourseRepository;
import com.krs.knowledgerevisingsystem.repository.ExamRepository;
import com.krs.knowledgerevisingsystem.repository.LessonExamRepository;
import com.krs.knowledgerevisingsystem.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExamServiceImplement implements ExamService{
    private final ExamRepository examRepository;
    private final LessonExamRepository lessonExamRepository;
    private final QuestionRepository questionRepository;
    private final CourseRepository courseRepository;
    private final SubjectService subjectService;

    public ExamServiceImplement(CourseRepository courseRepository, ExamRepository examRepository, LessonExamRepository lessonExamRepository, QuestionRepository questionRepository, SubjectService subjectService) {
        this.examRepository = examRepository;
        this.lessonExamRepository = lessonExamRepository;
        this.questionRepository = questionRepository;
        this.courseRepository = courseRepository;
        this.subjectService = subjectService;
    }

    @Override
    public List<Question> generateQuestions(Exam exam){
        List<Question> questions = new ArrayList<>();
        List<LessonExam> lessonExams = lessonExamRepository.findAllByExam(exam);

        for (LessonExam lessonExam : lessonExams){
//            System.out.println("LE: " + lessonExam.getNumberOfQuestions());
            List<Question> questionList = lessonExam.getLesson().getQuestions();
            if (!exam.isStatus()) {
                questionList = questionList.stream()
                        .filter(Question::isStatus)
                        .collect(Collectors.toList());
            }
            while (questionList.size() < lessonExam.getNumberOfQuestions()){
                questionList.addAll(lessonExam.getLesson().getQuestions());
            }
            if(!questionList.isEmpty())
            {
                Collections.shuffle(questionList);
                for(int i = 0; i < lessonExam.getNumberOfQuestions(); i++){
                    questions.add(questionList.get(i));
                }
            }
        }
        Collections.shuffle(questions);

        return questions;
    }

    @Override
    public Exam findById(Long examId) throws ExamInvalidException {
        Optional<Exam> exam = examRepository.findById(examId);
        if(exam.isPresent()){
            return exam.get();
        }
        else{
            throw new ExamInvalidException("Exam not found");
        }
    }

    @Transactional
    @Override
    public void saveExam(Exam exam) {
        examRepository.save(exam);
    }

    @Override
    public Boolean deleteExam(Long examId) throws ExamInvalidException {
        Optional<Exam> exam = examRepository.findById(examId);
        if(exam.isPresent()){
            examRepository.delete(exam.get());
            return true;
        }
        else{
            throw new ExamInvalidException("Exam not found");
        }
    }

    @Override
    public Page<Exam> findAllByCourseIdAndNameContaining(Long courseId, String name, Pageable pageable) {
        return examRepository.findAllByCourseIdAndNameContaining(courseId, name, pageable);
    }

    @Override
    public Page<Exam> findAllByStatusAndNameContaining(boolean status, String name, Pageable pageable) {
        return examRepository.findAllByStatusAndNameContaining(status, name, pageable);
    }

    @Override
    public Page<Exam> findAllByCourseIdAndStatusAndNameContaining(Long courseId, boolean status, String name, Pageable pageable) {
        return examRepository.findAllByCourseIdAndStatusAndNameContaining(courseId, status, name, pageable);
    }

    @Override
    public Page<Exam> findAll(Pageable pageable) {
        return examRepository.findAll(pageable);
    }

    @Override
    public Page<Exam> findAllExamWithStatus(User creator, boolean status, Pageable pageable){
        return examRepository.findAllByCreatorAndStatus(creator, status, pageable);
    }

    @Override
        public Page<Exam> findAllExam(String name, Long creatorId, Long subjectId, Long courseId, Boolean status, Pageable pageable){
        Page<Exam> pg =  examRepository.findAllByNameContainingIgnoreCaseAndStatusAndCreatorIdAndCourseIdAndSubjectId(name, status, creatorId, courseId, subjectId, pageable);
        return pg;
    }

    @Transactional
    @Override
    public Exam addExam(String name, String description, Long length, Long courseId, boolean status, short question) throws DataIntegrityViolationException {
        Exam exam = new Exam();
        exam.setName(name);
        exam.setDescription(description);
        exam.setLength(length);
        try {
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            if (!courseOptional.isPresent()) {
                throw new IllegalArgumentException("Course with id " + courseId + " does not exist");
            }
            exam.setCourse(courseOptional.get());
            exam.setStatus(status);
            exam.setNumberOfQuestions(question);
            // Save the exam first to ensure it has an ID
            Subject subject = courseOptional.get().getSubject();
            exam = examRepository.save(exam);
            exam.setCreator(courseOptional.get().getTeacher());
            exam.setSubject(subject);
            List<LessonExam> lessonExams = new ArrayList<>();

            for(Lesson lesson : subject.getLessons()){
                LessonExam lessonExam = new LessonExam();
                lessonExam.setExam(exam); // Set the saved exam
                lessonExam.setLesson(lesson);
                lessonExam.setNumberOfQuestions((short)0);
                lessonExams.add(lessonExam);
            }

            // Now save the LessonExams, which have a non-null Exam
            lessonExamRepository.saveAll(lessonExams);
        } catch (Exception e) {
            throw new DataIntegrityViolationException("Error saving exam and lesson exams", e);
        }
        return exam;
    }

    @Transactional
    @Override
    public Exam addPrivateExam(String name, String description, User creator, Long subjectId, Long length, boolean status, short question) throws DataIntegrityViolationException {
        Exam exam = new Exam();
        exam.setName(name);
        exam.setDescription(description);
        exam.setLength(length);
        exam.setStatus(status);
        exam.setNumberOfQuestions(question);
        exam.setCreator(creator);
        exam.setSubject(null);
        exam.setStatus(false);
        try {
            Subject subject = subjectService.getSubjectById(subjectId);
            exam.setSubject(subject);
            exam = examRepository.save(exam);

            List<LessonExam> lessonExams = new ArrayList<>();

            for(Lesson lesson : subject.getLessons()){
                LessonExam lessonExam = new LessonExam();
                lessonExam.setExam(exam); // Set the saved exam
                lessonExam.setLesson(lesson);
                lessonExam.setNumberOfQuestions((short)0);
                lessonExams.add(lessonExam);
            }

            lessonExamRepository.saveAll(lessonExams);
        } catch (Exception e) {
            throw new DataIntegrityViolationException("Error saving exam and lesson exams", e);
        }
        return exam;
    }

    @Override
    public Page<Exam> findAllByNameContaining(Pageable pageable, String name) {
        return examRepository.findAllByNameContaining(name, pageable);
    }

    @Override
    public void updateUsingSaveMethod(Long id, String name, Long length, short question, String description, boolean status) {
        Optional<Exam> examOptional = examRepository.findById(id);
        if (examOptional.isPresent()) {
            Exam exam = examOptional.get();
            exam.setName(name);
            exam.setLength(length);
            exam.setNumberOfQuestions(question);
            exam.setDescription(description);
            exam.setStatus(status);
            examRepository.save(exam);
        }
    }

    @Override
    public void updateByStatusId(Long id, boolean b) {
        Optional<Exam> examOptional = examRepository.findById(id);
        if (examOptional.isPresent()) {
            Exam exam = examOptional.get();
            exam.setStatus(b);
            examRepository.save(exam);
        }
    }

    @Override
    public List<Exam> getAllExam() {
        return examRepository.findAll();
    }

    @Override
    public List<Question> getAllQuestion(){
        return questionRepository.findAll();
    }

    @Override
    public List<Exam> getAllExamByCourseId(Long courseId) {
        return examRepository.findAllByCourseId(courseId);
    }

}
