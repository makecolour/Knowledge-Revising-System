package com.krs.knowledgerevisingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "subjects" )
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id", nullable = false)
    private Long id;

    @Column(name = "code",nullable = false,unique = true)
    private String code;

    @Column(name = "name",nullable = false)
    private String name;

    @Column( name = "description" ,length = 1000)
    private String description;

    @Column( name = "status" ,nullable = false)
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speciality", referencedColumnName = "role_id")
    private Role role;

    @OneToMany(mappedBy = "subject")
    private List<SubjectManager> subjectManager;

    @OneToMany(mappedBy = "subject")
    private List<Course> courses;

    @OneToMany(mappedBy = "subject")
    private List<Lesson> lessons;

    @Override
    public int hashCode() {
        return Objects.hash(id); // Assuming 'id' is a unique identifier for User
    }
}
