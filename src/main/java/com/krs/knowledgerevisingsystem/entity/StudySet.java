package com.krs.knowledgerevisingsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "study_set")
public class StudySet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "set_id", nullable = false)
    private Long id;

    @Column
    private String term;

    @Column
    private String definition;

    @Column
    private boolean status; //true: active, false: inactive

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false, referencedColumnName = "lesson_id")
    private Lesson lesson;
}
