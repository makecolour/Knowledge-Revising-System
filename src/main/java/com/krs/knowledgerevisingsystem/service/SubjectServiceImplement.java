package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.dto.SubjectDTO;
import com.krs.knowledgerevisingsystem.entity.Role;
import com.krs.knowledgerevisingsystem.entity.Subject;
import com.krs.knowledgerevisingsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImplement implements SubjectService {
    @Autowired
    private SubjectRepository repository;

    public SubjectServiceImplement(SubjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Subject> getAllSubjects() {
        return repository.findAll();
    }

    @Override
    public Subject getSubjectById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void saveSubject(Subject subject) throws DataIntegrityViolationException {
        try {
            repository.save(subject);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Duplicate entry for unique key");
        }
    }

    @Override
    public void deleteSubject(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Subject> searchSubjects(String keyword) {
        return repository.findByNameContainingOrCodeContaining(keyword, keyword);
    }

    @Override
    public Page<Subject> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

//    @Override
//    public Page<SubjectDTO> findSubjectByAll(int size, int page) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
//        return repository.findSubjectByAll(pageable);
//    }

//    public Object getFieldValue(Subject subject, String fieldName) {
//        try {
//            Field field = Subject.class.getDeclaredField(fieldName);
//            field.setAccessible(true);
//            return field.get(subject);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public Page<Subject> findByNameContainingOrCodeContaining(String name, String code, Pageable pageable, String column, String direction) {
        Page<Subject> subjects = repository.findByNameContainingOrCodeContaining(code, name, pageable);
        return sortSubjects(subjects, pageable, column, direction);
    }

    @Override
    public Page<Subject> findByStatusAndNameContainingOrCodeContaining(boolean status, String name, String code, Pageable pageable, String column, String direction) {
        Page<Subject> subjects = repository.findByStatusAndNameContainingOrCodeContaining(status, code, name, pageable);
        return sortSubjects(subjects, pageable, column, direction);
    }

    @Override
    public Page<Subject> findByStatusAndRole_IdAndNameContainingOrCodeContaining(Boolean status, Long speciality, String name, String code, Pageable pageable) {
        return repository.findByStatusAndRole_IdAndNameContainingOrCodeContaining(status, speciality, code, name, pageable);
    }

    @Override
    public Page<Subject> findAllByRole_IdAndNameContainingOrCodeContaining(Long speciality, String name, String code, Pageable pageable, String column, String direction) {
        Page<Subject> subjects = repository.findAllByRole_IdAndNameContainingOrCodeContaining(speciality, name, code, pageable);
        return sortSubjects(subjects, pageable, column, direction);
    }

    private Page<Subject> sortSubjects(Page<Subject> subjects, Pageable pageable, String column, String direction) {
        if (column == null || direction == null) {
            return subjects;
        }

        List<Subject> sortedSubjects = subjects.stream()
                .sorted((subject1, subject2) -> {
                    Object value1 = getFieldValue(subject1, column);
                    Object value2 = getFieldValue(subject2, column);
                    if (value1 instanceof Comparable && value2 instanceof Comparable) {
                        Comparable comparable1 = (Comparable) value1;
                        Comparable comparable2 = (Comparable) value2;
                        return "asc".equalsIgnoreCase(direction) ? comparable1.compareTo(comparable2) : comparable2.compareTo(comparable1);
                    }
                    return 0;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(sortedSubjects, pageable, subjects.getTotalElements());
    }

    private Object getFieldValue(Subject subject, String fieldName) {
        try {
            Field field = subject.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(subject);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateByStatusId(Long id, boolean b) {
        Subject subject = repository.findSubjectById(id);
        subject.setStatus(b);
        repository.save(subject);
    }

    @Override
    public void updateUsingSaveMethod(Long id, String code,String name, Role role, String description, boolean status) throws DataIntegrityViolationException {
        Subject subject = repository.findSubjectById(id);
        subject.setName(name);
        subject.setCode(code);
        subject.setRole(role);
        subject.setDescription(description);
        subject.setStatus(status);
        try {
            repository.save(subject);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Duplicate entry for unique key");
        }
    }

    @Override
    public List<Subject> getAllByStatus(boolean status) {
        return repository.findAllByStatus(status);
    }

    @Override
    public Page<Subject> findByStatusAndRole_IdAndNameContainingOrCodeContainingAndSubjectManagerContaining(Boolean status, Long speciality, String name, String code, Long manager, Pageable pageable) {
        return repository.findByStatusAndRole_IdAndNameContainingOrCodeContainingAndSubjectManagerContaining(status, speciality, name, code, manager, pageable);
    }


}



