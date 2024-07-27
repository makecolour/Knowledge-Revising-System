package com.krs.knowledgerevisingsystem.repository;

import com.krs.knowledgerevisingsystem.entity.Course;
import com.krs.knowledgerevisingsystem.entity.User;
import com.krs.knowledgerevisingsystem.entity.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.courseName = :name")
    Course findByCourseName(String name);

    @Query("SELECT c FROM Course c")
    List<Course> getAll();

    @Query("SELECT c FROM Course c WHERE c.subject.name = :name")
    List<Course> findAllBySubject_Name(String name);

    Optional<Course> findById(Long id);

    Page<Course> findByCourseNameContaining(Pageable pageable, String name);

    Page<Course> findByStatusAndAndCourseNameContaining(Pageable pageable, boolean status, String name);

    List<Course> findAllByRole_Id( long id);

    Page<Course> findAllBySubject_Name(Pageable pageable, String name);

    Page<Course> findAll(Pageable pageable);

    List<Course> findAllByTeacherAndStatus(User user, boolean status);

    Page<Course> findAllBySubject_IdAndCourseNameContaining(Pageable pageable, long id, String name);

    Page<Course> findAllBySubject_IdAndStatusAndCourseNameContaining(Pageable pageable, long id, boolean status, String name);

    Page<Course> findAllByStatusAndCourseNameContaining(Pageable pageable, boolean status, String name);

    Page<Course> findAllByStatus(Pageable pageable, boolean status);

    @Query("SELECT e FROM Course e WHERE " +
            "(?4 IS NULL OR e.teacher.id = ?4) AND " +
            "(?5 IS NULL OR e.subject.id = ?5) AND " +
            "LOWER(e.courseName) LIKE LOWER(CONCAT('%', ?1, '%')) AND " +
            "(?2 IS NULL OR e.status = ?2) AND " +
            "(?3 IS NULL OR e.role.id = ?3)")
    Page<Course> findAllByNameContainingIgnoreCaseAndStatusAndRoleIdAndUserIdAndSubjectId(String name, Boolean status, Long roleId, Long userId, Long subjectId, Pageable pageable);


}
