package com.krs.knowledgerevisingsystem.repository;

import com.krs.knowledgerevisingsystem.entity.Answer;
import com.krs.knowledgerevisingsystem.entity.AttemptHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptHistoryRepository extends JpaRepository<AttemptHistory, Long> {
    List<AttemptHistory> findAllByAttemptId(Long attemptId);
}
