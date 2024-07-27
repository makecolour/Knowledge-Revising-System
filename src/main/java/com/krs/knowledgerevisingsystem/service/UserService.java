package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.entity.Role;
import com.krs.knowledgerevisingsystem.entity.User;
import com.krs.knowledgerevisingsystem.entity.UserCode;
import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import com.krs.knowledgerevisingsystem.exception.ManageUser.UserInvalidException;
import org.springframework.scheduling.annotation.Scheduled;
import com.krs.knowledgerevisingsystem.entity.Role;

import java.util.List;
import java.util.Optional;

import java.util.List;

public interface UserService {
    Boolean save(String name, String password, String email, String role) throws SaveAccountInvalidException;

    User findByUser(String username);

    Integer generateCode();

    User findUserByEmail(String email) throws UserInvalidException;

    UserCode saveUserCode(User user, Integer code);

    boolean checkUserCode(Integer code, Long id);

    User findUserById(Long id) throws UserInvalidException;

    User savePassword(User user, String password);

    boolean checkPassword(User user, String password);

    User findById(Long id);

    @Scheduled(fixedRate = 10 * 60 * 1000)
        // 30 minutes in milliseconds
    void deleteUserCode();

    List<Role> getAllRole();

    void changePassword(User user, String newPassword);

    void changeProfile(User user, String name, String note);

    void deleteUser(User user);

    List<User> getAllUsers();

    List<User> getUserByRole(String role);

    List<Role> listRole();

    void createUser(String name, String role, String email);

    void banUser(User user);

    List<User>getAllUserByStatus(boolean status);

    void saveAll(List<User> users);

    List<User> saveAll2(List<User> users);
}
