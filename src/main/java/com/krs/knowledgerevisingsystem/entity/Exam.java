package com.krs.knowledgerevisingsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;
import java.util.List;

//Is the prototype of the exam
@Entity
@Data
@Table(name = "exams")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="length")
    private long length;

    @Column(name="number_of_questions")
    private short numberOfQuestions;

    @Column(name="status")
    private boolean status; //true: active, false: inactive

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Course course;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<Attempt> attemptList;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<LessonExam> lessonExams;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creator_id", referencedColumnName = "user_id")
    private User creator;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    private Subject subject;
}
