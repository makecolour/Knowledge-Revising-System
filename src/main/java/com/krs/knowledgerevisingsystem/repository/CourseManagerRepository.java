package com.krs.knowledgerevisingsystem.repository;

import com.krs.knowledgerevisingsystem.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseManagerRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findAllByCourseId(int courseId);
}
