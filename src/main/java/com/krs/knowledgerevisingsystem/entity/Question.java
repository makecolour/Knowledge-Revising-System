package com.krs.knowledgerevisingsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false)
    private Long id;

    @Column(name="level")
    private short level;

    @Column(name = "question_text")
    private String content;

    @Lob
    @Column(name="picture")
    private byte[] picture;

    @Column
    private boolean status; //true: active, false: inactive

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false, referencedColumnName = "lesson_id")
    private Lesson lesson;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Answer> answers;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AttemptHistory> attemptHistories;

}
