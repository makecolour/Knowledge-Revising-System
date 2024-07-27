package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.dto.LessonsDto;
import com.krs.knowledgerevisingsystem.entity.*;
import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import com.krs.knowledgerevisingsystem.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonServiceImplement implements LessonService {
    private final LessonRepository lessonRepository;

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final StudySetRepository studySetRepository;
    private final SubjectRepository subjectRepository;
    public LessonServiceImplement(SubjectRepository subjectRepository, LessonRepository lessonRepository, QuestionRepository questionRepository, StudySetRepository studySetRepository, AnswerRepository answerRepository) {
        this.lessonRepository = lessonRepository;
        this.questionRepository = questionRepository;
        this.studySetRepository = studySetRepository;
        this.subjectRepository = subjectRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public Boolean save(String name, Subject subject,int order,boolean status) throws SaveAccountInvalidException {
        try {

            Lesson newLesson = new Lesson();
            newLesson.setName(name);
            newLesson.setSubject(subject);
            newLesson.setStatus(status);
            newLesson.setOrder(order);
            lessonRepository.save(newLesson);
            return true;
        } catch (Exception e) {
            throw new SaveAccountInvalidException("Failed to save lesson");
        }
    }

    @Transactional
    @Override
    public Boolean saveAnswer(Long id, String content, boolean isCorrect, Question question) throws SaveAccountInvalidException {
        try {
            Answer answer = findAnswerWithId(id);
            answer.setContent(content);
            answer.setCorrect(isCorrect);
            answer.setQuestion(question);
            answerRepository.save(answer);
            return true;
        } catch (Exception e) {
            throw new SaveAccountInvalidException("Failed to save answer");
        }
    }

    @Transactional
    @Override
    public Boolean addAnswer(String content, boolean isCorrect, Question question) throws SaveAccountInvalidException {
        try {
            Answer answer = new Answer();
            answer.setContent(content);
            answer.setCorrect(isCorrect);
            answer.setQuestion(question);
            answerRepository.save(answer);
            return true;
        } catch (Exception e) {
            throw new SaveAccountInvalidException("Failed to save answer");
        }
    }

    @Override
    public Answer findAnswerWithId(Long id) {
        return answerRepository.findAnswerById(id);
    }

    @Override
    public List<Lesson> getAllLessonSameSubject(Long subjectId) {
        return lessonRepository.findALLBySubject_Id(subjectId);
    }

    @Override
    public Page<LessonsDto> findLessonByAll(int size, int page, String id, String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        return lessonRepository.findLessonsByAll(pageable, id, keyword);
    }

    @Override
    public Question getQuestionByContentAndLesson(String content, Long lessonId) {
        Lesson l = lessonRepository.findLessonById(lessonId);
        return questionRepository.findQuestionByContentAndLesson(content, l);
    }

    @Override
    public Page<Question> findQuestionByAll(int size, int page, String id, String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        return questionRepository.findQuestionByAll(pageable, id, keyword);
    }

    @Override
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    @Override
    public Page<StudySet> findStudySetByAll(int size, int page, String id, String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        return studySetRepository.findStudySetByAll(pageable, id, keyword);
    }

    @Override
    public Boolean deleteQuestion(String id) {
        Optional<Question> questionOptional = questionRepository.findById(Long.valueOf(id));
        if (questionOptional.isPresent()) {
            questionRepository.deleteById(Long.valueOf(id));
            return true;
        }
        return false;
    }

    @Override
    public Boolean editQuestion(Long id, String content, short  level, boolean status) {
        Optional<Question> questionOpt = questionRepository.findById(id);
        if (questionOpt.isPresent()) {
            Question question = questionOpt.get();
            question.setContent(content);
            question.setLevel(level);
            question.setStatus(status);
            questionRepository.save(question);
            return true;
        }
        return false;
    }

    @Override
    public List<Question> getAllQuestionByStatus(boolean status) {
        return questionRepository.findAllByStatus(status);
    }

    @Override
    public Boolean addQuestion(String content, short level, boolean status, Long lessonsId) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonsId);
        if (lessonOptional.isPresent()) {
            Question question = new Question();
            question.setContent(content);
            question.setLevel(level);
            question.setStatus(status);
            question.setLesson(lessonOptional.get());
            questionRepository.save(question);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Question findQuestionByIdNonOp(Long id) {
        return questionRepository.findQuestionById(id);
    }

    @Override
    public List<Answer> findAnswerByQuestionId(Long questionId) {
        return answerRepository.findAllByQuestionId(questionId);
    }

    @Override
    public Optional<Question> findQuestionById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<StudySet> findAllStudySet(String lessonId) {
        return studySetRepository.findByLessonId(Long.valueOf(lessonId));
    }

    @Override
    public Boolean addStudySet(String term, String definition, boolean status, Long lessonId) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);
        if (lessonOptional.isPresent()) {
            StudySet studySet = new StudySet();
            studySet.setTerm(term);
            studySet.setDefinition(definition);
            studySet.setStatus(status);
            studySet.setLesson(lessonOptional.get());
            studySetRepository.save(studySet);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateStudySet(Long id, String term, String definition, boolean status) {
        Optional<StudySet> studySetOptional = studySetRepository.findById(id);
        if (studySetOptional.isPresent()) {
            StudySet studySet = studySetOptional.get();
            studySet.setTerm(term);
            studySet.setDefinition(definition);
            studySet.setStatus(status);
            studySetRepository.save(studySet);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteStudySet(Long id) {
        if (studySetRepository.existsById(id)) {
            studySetRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateByStatusId(Long id, boolean b) {
        Lesson lesson = lessonRepository.findLessonById(id);
        lesson.setStatus(b);
        lessonRepository.save(lesson);
    }

    @Override
    public Lesson findLessonById(Long id) {
        return lessonRepository.findLessonById(id);
    }

    @Override
    public void updateLesson(Long id, String title, boolean status, Long subjectId, int order) throws DataIntegrityViolationException {
        try {
            Subject subject = subjectRepository.findSubjectById(subjectId);
            Lesson lesson = lessonRepository.findLessonById(id);
            lesson.setName(title);
            lesson.setStatus(status);
            lesson.setSubject(subject);
            lesson.setOrder(order);
            lessonRepository.save(lesson);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Failed to update lesson");
        }
    }

    @Override
    public Lesson findLessonBySubject_Id(Long id) {
        return lessonRepository.findLessonBySubject_Id(id);
    }

    @Override
    public Page<Lesson> findAllBySubject_Id(Long id, int size, Pageable pageable) {
        return lessonRepository.findAllBySubject_Id(id, pageable);
    }

    @Override
    public Page<StudySet> findStudySetByLessonId(int size, int page, Long id) {
        Pageable pageable = PageRequest.of(page, size);
        return studySetRepository.findAllByLessonId(id, pageable);
    }
    @Override
    public Page<Lesson> getAll(Pageable pageable) {
        return lessonRepository.findAllLessons(pageable);
    }

    @Override
    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }
    public Page<Lesson> findAllByNameContaining(String name, Pageable pageable, String column, String direction) {
        Page<Lesson> lessons = lessonRepository.findAllByNameContaining(name, pageable);
        return sortLessons(lessons, pageable, column, direction);
    }

    @Override
    public List<StudySet> findAllStudySet() {
        return studySetRepository.findAll();
    }

    @Override
    public Question getQuestionByContent(String content) {
        return questionRepository.findQuestionByContent(content);
    }

    @Override
    public List<Answer> findAnswerById(Long id) {
        return answerRepository.findAllByQuestionId(id);
    }

    public Page<Lesson> findAllByNameContainingAndStatus(Pageable pageable, String name, boolean status, String column, String direction) {
        Page<Lesson> lessons = lessonRepository.findAllByNameContainingAndStatus(pageable, name, status);
        return sortLessons(lessons, pageable, column, direction);
    }

    public Page<Lesson> findAllByNameContainingAndStatusAndSubjectId(Pageable pageable, String name, boolean status, Long subjectId, String column, String direction) {
        Page<Lesson> lessons = lessonRepository.findAllByNameContainingAndStatusAndSubjectId(pageable, name, status, subjectId);
        return sortLessons(lessons, pageable, column, direction);
    }

    public Page<Lesson> findAllByNameContainingAndSubjectId(Pageable pageable, String name, Long subjectId, String column, String direction) {
        Page<Lesson> lessons = lessonRepository.findAllByNameContainingAndSubjectId(pageable, name, subjectId);
        return sortLessons(lessons, pageable, column, direction);
    }

    private Page<Lesson> sortLessons(Page<Lesson> lessons, Pageable pageable, String column, String direction) {
        if (column == null || direction == null) {
            return lessons;
        }
        List<Lesson> sortedLessons = lessons.stream()
                .sorted((lesson1, lesson2) -> {
                    Object value1 = getFieldValue(lesson1, column);
                    Object value2 = getFieldValue(lesson2, column);
                    if (value1 instanceof Comparable && value2 instanceof Comparable) {
                        Comparable comparable1 = (Comparable) value1;
                        Comparable comparable2 = (Comparable) value2;
                        return "asc".equalsIgnoreCase(direction) ? comparable1.compareTo(comparable2) : comparable2.compareTo(comparable1);
                    }
                    return 0;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(sortedLessons, pageable, lessons.getTotalElements());
    }

    private Object getFieldValue(Lesson lesson, String fieldName) {
        try {
            Field field = Lesson.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(lesson);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAnswer(Answer a) {
        answerRepository.deleteById(a.getId());
    }

}
