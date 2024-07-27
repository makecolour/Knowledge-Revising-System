package com.krs.knowledgerevisingsystem.repository;

import com.krs.knowledgerevisingsystem.entity.Subject;
import com.krs.knowledgerevisingsystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserListRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    Page<User> findByFullNameContaining(String fullName, Pageable pageable);

    Page<User> findAll(Pageable pageable);

    Page<User> findAllByRole_Id(Long id, Pageable pageable);

    List<User> findByRole_Id(Long id);

}
