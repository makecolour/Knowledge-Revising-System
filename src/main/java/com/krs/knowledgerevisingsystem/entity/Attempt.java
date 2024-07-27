package com.krs.knowledgerevisingsystem.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

//hold the information of the user's attempt to an exam include questions and answers
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attempt")
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attempt_id", nullable = false)
    private Long id;

    @Column(name="mark")
    private int mark;

    @Column(name="attempt_date")
    private Timestamp attemptDate;

    @Column(name="status")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "exam_id", referencedColumnName = "exam_id")
    private Exam exam;

    @OneToMany(mappedBy = "attempt")
    private List<AttemptHistory> attemptHistories;
}
