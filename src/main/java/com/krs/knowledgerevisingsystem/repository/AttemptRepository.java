package com.krs.knowledgerevisingsystem.repository;

import com.krs.knowledgerevisingsystem.entity.Attempt;
import com.krs.knowledgerevisingsystem.entity.Exam;
import com.krs.knowledgerevisingsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Long>{
    List<Attempt> findAllByUserIdAndExamId(Long userId, Long examId);
    Optional<Attempt> findById(Long attemptId);

    List<Attempt> findAllByExamAndUser(Exam exam, User user);
    @Query("SELECT a FROM Attempt a")
    List<Attempt> findAllAttempts();

    @Query("SELECT a.exam.name, COUNT(a) FROM Attempt a GROUP BY a.exam.name ORDER BY COUNT(a) DESC")
    List<Object[]> findExamWithMostAttempts();
}
