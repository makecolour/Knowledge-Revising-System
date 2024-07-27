package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.dto.EnrollmentDTO;
import com.krs.knowledgerevisingsystem.entity.Course;
import com.krs.knowledgerevisingsystem.entity.Enrollment;
import com.krs.knowledgerevisingsystem.entity.User;
import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EnrollmentService {
    Enrollment  add(Enrollment enrollment);

    Boolean save(User user, Course course) throws SaveAccountInvalidException;

    List<Enrollment> getAllUserSameCourse(Long course);

    Boolean checkEnrollment(User user, Course course);

    void deleteEnrollment(User user, Course course);

    List<EnrollmentDTO> findTopCoursesByEnrollments();

    Page<Enrollment> findAllByCourseId(Long id, Pageable pageable);

    Page<Enrollment> findAll(Pageable pageable);

    Page<Enrollment> findAllByUserId(Pageable pageable, Long id);

    Page<Enrollment> findAllByCourse_CourseNameContainingAndUserIdAndCourse_Status(Pageable pageable, String name, Long id, boolean status);

    Page<Enrollment> findAllByUserIdAndCourse_Subject_Name(Pageable pageable, Long id, String name);

    Page<Enrollment> findAllByCourseIdAndUser_FullNameContaining(Long id, String name, Pageable pageable);

    Page<Enrollment> findAllByCourseIdAndUser_FullNameContainingAndUser_Role_Name(Long id, String name, String role, Pageable pageable);

    Page<Enrollment> findAllByCourseIdAndUser_FullNameContainingAndUser_Status(Long courseId, String name, boolean status2, Pageable pageable);

    Page<Enrollment> findAllByCourseIdAndUser_FullNameContainingAndUser_Role_NameAndUser_Status(Long courseId, String name, String role, boolean status2, Pageable pageable);

    Page<Enrollment> findAllByUserIdAndCourse_CourseNameContainingAndCourse_Subject_Id(Pageable pageable, Long id, String name, long subjectId);

    List<Enrollment> findAllByUserId(Long userId);

    Page<Enrollment> findAllByUserIdAndCourse_Status(Pageable pageable, Long id, boolean status);

}
