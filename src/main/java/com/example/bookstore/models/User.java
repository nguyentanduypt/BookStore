package com.example.bookstore.models;

import com.example.bookstore.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "\"user\"")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

//    private UserStatus status;
    private Instant createdTime;
    private Instant updatedTime;
    private UserStatus status;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
