package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.entity.Subject;
import com.krs.knowledgerevisingsystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserListService {
    public List<User> getAllUser();
    Subject getUserById(Long id);


    List<User> searchUser(String keyword);
    Page<User> findByFullNameContaining(String name, Pageable pageable);
    Page<User> findAll(Pageable pageable);
}
