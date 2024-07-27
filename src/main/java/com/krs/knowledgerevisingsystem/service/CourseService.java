package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.entity.Course;
import com.krs.knowledgerevisingsystem.entity.Role;
import com.krs.knowledgerevisingsystem.entity.Subject;
import com.krs.knowledgerevisingsystem.entity.User;
import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


public interface CourseService {
    Boolean save(Role role, String courseName, User teacherId, Subject subjectId, String description, boolean status) throws SaveAccountInvalidException;

    List<Course> getAllCourse();

    Optional<Course> findById(Long id);

    Course findCourseByName(String name);

    Page<Course> findByCourseNameContaining(Pageable pageable, String name, String column, String direction);

    Page<Course> findAll(Pageable pageable);

    Page<Course> findAllCourseBySubjectContaining(Pageable pageable, String name, String column, String direction);

    Page<Course> findAllBySubject_IdAndCourseNameContaining(Pageable pageable, long id, String name, String column, String direction);

    void updateCourse(Long id, String courseName, String description, boolean status, Subject subjectId, User teacherId, Role role);

    Page<Course> findAllBySubject_IdAndStatusAndCourseNameContaining(Pageable pageable, long id, boolean status, String name, String column, String direction);

    Page<Course> findAllByStatusAndCourseNameContaining(Pageable pageable, boolean status, String name, String column, String direction);

    Page<Course> findAllByStatus(Pageable pageable, boolean status);

    void updateByStatusId(Long id, boolean b);

    void saveUserToDatabase(MultipartFile file) throws Exception;

    List<Course> findAllByRole_Id( long id);

    List<Course> getAllCourseByTeacher(User user);


    List<Course> getAllCourseByManager(User user);
    Page<Course> findAllByNameContainingIgnoreCaseAndStatusAndRoleIdAndUserIdAndSubjectId(String name, Boolean status, Long roleId, Long userId, Long subjectId, Pageable pageable, String column, String direction);
}
