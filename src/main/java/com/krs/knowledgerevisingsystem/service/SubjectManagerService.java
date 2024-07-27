package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.entity.Subject;
import com.krs.knowledgerevisingsystem.entity.SubjectManager;
import com.krs.knowledgerevisingsystem.entity.User;
import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubjectManagerService {
    Page<SubjectManager> findAll(Pageable pageable);

    List<SubjectManager> findAll();

    void save(Subject subject, User user);

    void updateManager(Long id, Long manager)throws DataIntegrityViolationException, SaveAccountInvalidException;

    Page<SubjectManager> findAllBySubject_NameContainingOrSubject_CodeContaining(String name, String code, Pageable pageable,String column, String direction);

    Page<SubjectManager> findAllBySubject_StatusAndSubject_NameContainingOrSubject_CodeContaining(boolean status, String name, String code, Pageable pageable,String column, String direction);

    Page<SubjectManager> findAllBySubject_StatusAndSubject_Role_IdAndSubject_NameContainingOrSubject_CodeContaining(boolean status, Long speciality, String name, String code, Pageable pageable,String column, String direction);

    Page<SubjectManager> findAllBySubject_Role_IdAndSubject_NameContainingOrSubject_CodeContaining( Long speciality, String name, String code, Pageable pageable,String column, String direction);


    Page<SubjectManager> findAllBySubject_StatusAndUser_IdAndSubject_NameContainingOrSubject_CodeContaining( boolean status,  Long id,  String name,  String code, Pageable pageable,String column, String direction);

    Page<SubjectManager> findAllBySubject_StatusAndUser_IdAndSubject_Role_IdAndSubject_NameContainingOrSubject_CodeContaining( boolean status,  Long id,  Long speciality, String name,  String code, Pageable pageable,String column, String direction);

    Page<SubjectManager> findAllBySubject_Role_IdAndUser_IdAndSubject_NameContainingOrSubject_CodeContaining( Long speciality,  Long id,  String name, String code, Pageable pageable,String column, String direction);

    Page<SubjectManager> findAllByUser_Id(Long id, Pageable pageable);

    Page<SubjectManager> findAllByUser_IdAndSubject_NameContainingOrSubject_CodeContaining(Long id, String name, String code, Pageable pageable,String column, String direction);
}


