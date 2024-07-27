package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.entity.Role;
import com.krs.knowledgerevisingsystem.entity.User;
import com.krs.knowledgerevisingsystem.entity.UserCode;
import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import com.krs.knowledgerevisingsystem.exception.ManageUser.UserInvalidException;
import com.krs.knowledgerevisingsystem.repository.CourseManagerRepository;
import com.krs.knowledgerevisingsystem.repository.RoleRepository;
import com.krs.knowledgerevisingsystem.repository.UserCodeRepository;
import com.krs.knowledgerevisingsystem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.*;

@Service
public class UserServiceImplement implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private UserCodeRepository userCodeRepository;

    private CourseManagerRepository courseManagerRepository;

    private EmailService emailService;


    public UserServiceImplement(UserRepository userRepository, RoleRepository roleRepository, UserCodeRepository userCodeRepository, CourseManagerRepository courseManagerRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userCodeRepository = userCodeRepository;
        this.courseManagerRepository = courseManagerRepository;
        this.emailService = emailService;
    }

    @Override
    public Boolean save(String name, String password, String email, String roleName) throws SaveAccountInvalidException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String encodedPassword = encoder.encode(password);

        if (userRepository.findByEmail(email) != null) {
            throw new SaveAccountInvalidException("User with email " + email + " already exists");
        }

        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }

        User user = new User();
        user.setFullName(name);
        user.setPassword(encodedPassword);
        user.setEmail(email);
        user.setRole(role);
        user.setStatus(true);
        userRepository.save(user);
        return true;
    }


    @Override
    public User findByUser(String username) {
        return userRepository.findByEmail(username);
    }
    //TODO

    @Override
    public Integer generateCode() {
        Random rand = new Random();
        Integer myInteger = rand.nextInt(900000) + 100000;
        return myInteger;
    }

    @Override
    public User findUserByEmail(String email) throws UserInvalidException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserInvalidException("Email not found");
        } else {
            return user;
        }
    }

    @Override
    public UserCode saveUserCode(User user, Integer code) {
        UserCode userCode = userCodeRepository.findByUserId(user.getId());
        if (userCode != null) {
            userCode = userCodeRepository.findByUserId(user.getId());
            userCode.setCode(code);
            userCode.setTime(new Timestamp(System.currentTimeMillis()));
            userCodeRepository.save(userCode);
        } else {
            userCode = new UserCode();
            userCode.setCode(code);
            userCode.setUser(user);
            userCode.setTime(new Timestamp(System.currentTimeMillis()));
            userCodeRepository.save(userCode);
        }
        return userCode;
    }

    @Override
    public User savePassword(User user, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return user;
    }

    @Override
    public boolean checkPassword(User user, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(password, user.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean checkUserCode(Integer code, Long id) {
        UserCode userCode = userCodeRepository.findById(id).orElse(null);
        return userCode != null && Objects.equals(userCode.getCode(), code) && (System.currentTimeMillis() - userCode.getTime().getTime() < 30000 * 60);
    }

    @Override
    public User findUserById(Long id) throws UserInvalidException {
        return userRepository.findById(id).orElseThrow(() -> new UserInvalidException("User not found"));
    }

    @Override
    @Scheduled(fixedRate = 60 * 10000) // 10 minutes in milliseconds
    public void deleteUserCode() {
        userCodeRepository.updateUserCodeToNullIfTimeIsMoreThan30MinutesAgo();
    }



    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }


    @Override
    public void changePassword(User user, String newPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public void changeProfile(User user, String name, String note) {
        user.setFullName(name);
        user.setNote(note);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> getUserByRole(String role) {
        return userRepository.findByRoleName(role);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> listRole() {
        return roleRepository.findAll();
    }


    @Override
    public void createUser(String name, String roles, String email) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String randomPassword = generateRandomPassword();
        emailService.sendSMTP("Knowledge Revising System", email, "Account", "Your verification code is: " + randomPassword);
        String encodedPassword = encoder.encode(randomPassword);
        Optional<Role> roleOptional = roleRepository.findById(Long.valueOf(roles));
        if (!roleOptional.isPresent()) {
            throw new IllegalArgumentException("Invalid role ID: " + roles);
        }
        Role role = roleOptional.get();
        User user = new User();
        user.setFullName(name);
        user.setRole(role);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setStatus(true);
        userRepository.save(user);
    }

    @Override
    public void banUser(User user) {
        user.setStatus(false);
        userRepository.save(user);
        User updatedUser = userRepository.findById(user.getId()).orElse(null);
        if (updatedUser != null && !updatedUser.isStatus()) {
            System.out.println("User status has been successfully updated to false");
        } else {
            System.out.println("Failed to update user status to false");
        }
    }

    @Override
    public List<User> getAllUserByStatus(boolean status) {
        return userRepository.findAllByStatus(status);
    }

    @Override
    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }

    @Override
    public List<User> saveAll2(List<User> users) {
        List<User> savedUsers = new ArrayList<>();
        for (User user : users) {
            User savedUser = userRepository.save(user);
            savedUsers.add(savedUser);
        }
        return savedUsers;
    }

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        List<Character> passwordCharacters = new ArrayList<>();

        passwordCharacters.add((char) ('A' + random.nextInt(26)));

        passwordCharacters.add((char) ('a' + random.nextInt(26)));

        passwordCharacters.add((char) (33 + random.nextInt(15)));

        for (int i = 0; i < 3; i++) {
            passwordCharacters.add((char) ('0' + random.nextInt(10)));
        }

        Collections.shuffle(passwordCharacters, random);

        StringBuilder password = new StringBuilder();
        for (char c : passwordCharacters) {
            password.append(c);
        }

        return password.toString();
    }
}


