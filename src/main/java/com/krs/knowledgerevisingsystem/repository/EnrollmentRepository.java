package com.krs.knowledgerevisingsystem.repository;

import com.krs.knowledgerevisingsystem.dto.EnrollmentDTO;
import com.krs.knowledgerevisingsystem.entity.Course;
import com.krs.knowledgerevisingsystem.entity.Enrollment;
import com.krs.knowledgerevisingsystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findAllByUserId(Long userId);

    List<Enrollment> findAllByCourseId(Long courseId);

    Enrollment findByUserIdAndCourseId(Long userId, Long courseId);

    @Query(value = "SELECT " +
            "    e.course_id AS courseId, " +
            "    COUNT(*) AS totalEnrollments " +
            "FROM " +
            "    krs.enrollment e " +
            "JOIN " +
            "    krs.course c ON e.course_id = c.course_id " +
            "GROUP BY " +
            "    e.course_id " +
            "ORDER BY " +
            "    totalEnrollments DESC " +
            "LIMIT 8", nativeQuery = true)
    List<EnrollmentDTO> findTopCoursesByEnrollments();

    Page<Enrollment> findAllByCourseId(Long courseId, Pageable pageable);

    Page<Enrollment> findAllByCourseIdAndUser_FullNameContaining(Long id, String name, Pageable pageable);

    Page<Enrollment> findAllByCourseIdAndUser_FullNameContainingAndUser_Role_Name(Long id, String name, String role, Pageable pageable);

    Page<Enrollment> findAllByCourseIdAndUser_FullNameContainingAndUser_Status(Long id, String name, Boolean status, Pageable pageable);
    
    Page<Enrollment> findAllByCourseIdAndUser_FullNameContainingAndUser_Role_NameAndUser_Status(Long id, String name, String role, Boolean status, Pageable pageable);

    Page<Enrollment> findAllByUserId(Pageable pageable, Long id);

    Page<Enrollment> findAllByCourse_CourseNameContainingAndUserIdAndCourse_Status(Pageable pageable, String name, Long id, boolean status);

    Page<Enrollment> findAllByUserIdAndCourse_Subject_Name(Pageable pageable, Long id, String name);

    Page<Enrollment> findAllByUserIdAndCourse_CourseNameContainingAndCourse_Subject_Id(Pageable pageable, Long id, String name, long subjectId);

    Page<Enrollment> findAllByUserIdAndCourse_Status(Pageable pageable, Long id, boolean status);


}
