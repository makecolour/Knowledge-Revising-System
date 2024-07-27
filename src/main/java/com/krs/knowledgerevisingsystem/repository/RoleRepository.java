package com.krs.knowledgerevisingsystem.repository;

import com.krs.knowledgerevisingsystem.entity.Role;
import com.krs.knowledgerevisingsystem.entity.User;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    @NotNull
    List<Role> findAll();

    @NotNull
    Optional<Role> findById(Long id);

    Page<Role> findAllByNameContaining(Pageable pageable, String name);

    Page<Role> findAllByNameContainingAndSpeciality(Pageable pageable, String name, String speciality);

    Page<Role> findAllByNameContainingAndStatus(Pageable pageable, String name, boolean status);

    Page<Role> findAllByNameContainingAndStatusAndSpeciality(Pageable pageable, String name, boolean status, String speciality);

    @NotNull
    Page<Role> findAll(@NotNull Pageable pageable);

    Optional<Role> getRoleById(Long id);


}
