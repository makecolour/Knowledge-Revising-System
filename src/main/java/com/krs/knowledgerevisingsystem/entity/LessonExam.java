package com.krs.knowledgerevisingsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "lesson_exam")
public class LessonExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leson_exam_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false, referencedColumnName = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false, referencedColumnName = "exam_id")
    private Exam exam;

    @Column(name="number_of_questions")
    private short numberOfQuestions;
}
