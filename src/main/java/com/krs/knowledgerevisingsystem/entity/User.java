package com.krs.knowledgerevisingsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@ToString
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id",nullable = false)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password; //tobe hashed
    private String fullName;
//    private byte role; //0: guest, 1: student, 2: teacher, 3: manager, 4: admin
    @Lob
    private byte[] avatar; //avatar
    private boolean status; //true: active, false: inactive
    private String accessToken; //Google login
    private String note; //note of admin

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserCode userCode;

    @OneToMany(mappedBy = "user")
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "user")
    private List<SubjectManager> subjectManager;

    @OneToMany(mappedBy = "user")
    private List<Attempt> attempts;


    @OneToMany(mappedBy = "user")
    private List<Contact> contacts;

    @Override
    public int hashCode() {
        return Objects.hash(id); // Assuming 'id' is a unique identifier for User
    }
}
