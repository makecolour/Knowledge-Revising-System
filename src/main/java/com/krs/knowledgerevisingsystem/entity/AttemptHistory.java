package com.krs.knowledgerevisingsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

//hold the information of the user's attempt questions and answers
@Entity
@Data
@Table(name = "attempt_history")
public class AttemptHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attempt_history_id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attempt_id", referencedColumnName = "attempt_id")
    private Attempt attempt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private Question question;

    @OneToMany(mappedBy = "attemptHistory", cascade = CascadeType.ALL)
    private List<AttemptHistoryAnswer> attemptHistoryAnswers;
}
