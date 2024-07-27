package com.krs.knowledgerevisingsystem.repository;

import com.krs.knowledgerevisingsystem.entity.AttemptHistoryAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptHistoryAnswerRepository extends JpaRepository<AttemptHistoryAnswer, Long> {

}