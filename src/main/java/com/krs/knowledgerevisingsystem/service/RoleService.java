package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.entity.Role;
import com.krs.knowledgerevisingsystem.entity.Subject;
import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RoleService {
    Boolean save( String name, String description, String speciality, boolean status, Long priority) throws DataIntegrityViolationException, SaveAccountInvalidException;

    Page<Role> findAllByNameContaining(String name, String column, String direction, Pageable pageable);

    Page<Role> findAllByNameContainingAndSpeciality(Pageable pageable, String name, String speciality, String column, String direction);

    Page<Role> findAllByNameContainingAndStatus(Pageable pageable, String name, boolean status,String column, String direction);

    Page<Role> findAllByNameContainingAndStatusAndSpeciality(Pageable pageable, String name, boolean status, String speciality,String column, String direction);

    Page<Role> findAll(Pageable pageable);

    List<Role> findAll();

    Optional<Role> getRoleById(Long id);

    void updateUsingSaveMethod( Long id, String name, String speciality, String description, Boolean status, Long priority) throws DataIntegrityViolationException, SaveAccountInvalidException;

    void updateByStatusId(Long id, boolean status);
}
