package com.krs.knowledgerevisingsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user_code")
public class UserCode {
    @Id
    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "code")
    private Integer code;

    @Column
    private Timestamp time;

    @PreUpdate
    public void onPreUpdate() {
        this.time = new Timestamp(System.currentTimeMillis());
    }

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public int hashCode() {
        return Objects.hash(userId); // Assuming 'id' is a unique identifier for User
    }

}