package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.dto.SubjectDTO;
import com.krs.knowledgerevisingsystem.entity.Role;
import com.krs.knowledgerevisingsystem.entity.Subject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubjectService {
    List<Subject> getAllSubjects();

    Subject getSubjectById(Long id);

    void saveSubject(Subject subject) throws DataIntegrityViolationException;

    void deleteSubject(Long id);

    List<Subject> searchSubjects(String keyword);

    Page<Subject> findByNameContainingOrCodeContaining(String name, String code, Pageable pageable, String column, String direction);

    Page<Subject> findAll(Pageable pageable);

//    Page<SubjectDTO> findSubjectByAll(int size, int page);

    Page<Subject> findByStatusAndNameContainingOrCodeContaining(boolean status, String name, String code, Pageable pageable, String column, String direction);

    Page<Subject> findByStatusAndRole_IdAndNameContainingOrCodeContaining(Boolean status, Long speciality, String name, String code, Pageable pageable);

    Page<Subject> findAllByRole_IdAndNameContainingOrCodeContaining(Long speciality, String name, String code, Pageable pageable, String column, String direction);

    void updateByStatusId(Long id, boolean b);

    void updateUsingSaveMethod(Long id,String code, String name, Role role, String description, boolean status) throws DataIntegrityViolationException;

    List<Subject> getAllByStatus(boolean status);

    Page<Subject> findByStatusAndRole_IdAndNameContainingOrCodeContainingAndSubjectManagerContaining(Boolean status, Long speciality, String name, String code, Long manager, Pageable pageable);

}

