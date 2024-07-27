package com.krs.knowledgerevisingsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "attempt_history_answer")
public class AttemptHistoryAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attempt_history_id", referencedColumnName = "attempt_history_id")
    private AttemptHistory attemptHistory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id", referencedColumnName = "answer_id")
    private Answer answer;
}