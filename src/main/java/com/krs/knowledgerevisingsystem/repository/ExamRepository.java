package com.krs.knowledgerevisingsystem.repository;

import com.krs.knowledgerevisingsystem.entity.Exam;
import com.krs.knowledgerevisingsystem.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    Optional<Exam> findById(Long examId);

    Page<Exam> findAllByNameContaining(String name, Pageable pageable);

    Page<Exam> findAllByCourseIdAndNameContaining(Long courseId,String name, Pageable pageable);

    Page<Exam> findAllByStatusAndNameContaining(boolean status ,String name, Pageable pageable);

    Page<Exam> findAllByCourseIdAndStatusAndNameContaining(Long courseId,boolean status,String name, Pageable pageable);

    @NotNull
    Page<Exam> findAll(@NotNull Pageable pageable);

    Page<Exam> findAllByCreatorAndStatus(User creator, boolean status, Pageable pageable);

    Page<Exam> findAllByCreatorIdAndStatusAndSubjectIdAndNameContaining(Long creatorId, boolean status, Long subjectId, String name, Pageable pageable);
    @Query("SELECT e FROM Exam e WHERE " +
            "(?4 IS NULL OR e.course.id = ?4) AND " +
            "(?5 IS NULL OR e.subject.id = ?5) AND " +
            "LOWER(e.name) LIKE LOWER(CONCAT('%', ?1, '%')) AND " +
            "(?2 IS NULL OR e.status = ?2) AND " +
            "(?3 IS NULL OR e.creator.id = ?3)")
    Page<Exam> findAllByNameContainingIgnoreCaseAndStatusAndCreatorIdAndCourseIdAndSubjectId(String name, Boolean status, Long creatorId, Long courseId, Long subjectId, Pageable pageable);

    List<Exam> findAllByCourseId(Long courseId);

}
