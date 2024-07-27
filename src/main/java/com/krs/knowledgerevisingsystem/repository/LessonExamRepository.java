package com.krs.knowledgerevisingsystem.repository;

import com.krs.knowledgerevisingsystem.entity.Exam;
import com.krs.knowledgerevisingsystem.entity.LessonExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonExamRepository extends JpaRepository<LessonExam, Long> {

    List<LessonExam> findAllByExamId(Long examId);
    List<LessonExam> findAllByExam(Exam exam);
}
