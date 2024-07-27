package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.entity.Subject;
import com.krs.knowledgerevisingsystem.entity.User;
import com.krs.knowledgerevisingsystem.repository.SubjectRepository;
import com.krs.knowledgerevisingsystem.repository.UserListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserListServiceImplement implements UserListService{
    @Autowired
    private UserListRepository repository;

    @Override
    public List<User> getAllUser() {
        return repository.findAll();
    }

    @Override
    public Subject getUserById(Long id) {
        return null;
    }


    @Override
    public List<User> searchUser(String keyword) {
        return List.of();
    }



    public Page<User> findByFullNameContaining(String keyword, Pageable pageable) {
        return repository.findByFullNameContaining(keyword, pageable);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
