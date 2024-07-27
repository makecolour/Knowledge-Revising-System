package com.krs.knowledgerevisingsystem.repository;

import com.krs.knowledgerevisingsystem.entity.Role;
import com.krs.knowledgerevisingsystem.entity.Subject;
import com.krs.knowledgerevisingsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Optional<User> findById(Long id);

    List<User> findAll();

    List<User> findByRole_Id(Long id);

    List<User> findByRoleName(String role_name);

    List<User> findAllByStatus(boolean role);

}
