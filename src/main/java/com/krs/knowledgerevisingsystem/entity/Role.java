package com.krs.knowledgerevisingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Year;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role {
    @Id
    @Column(name = "role_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true)
    public String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column
    private boolean status; //true: active, false: inactive

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "priority")
    private Long priority;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subject> subjects;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> courses;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;

    @Override
    public int hashCode() {
        return Objects.hash(id); // Assuming 'id' is a unique identifier for User
    }
}
