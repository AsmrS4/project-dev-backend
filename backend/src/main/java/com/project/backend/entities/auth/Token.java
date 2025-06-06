package com.project.backend.entities.auth;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "blacklist")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userId")
    private UUID userId;
    @Column(name = "token", unique = true)
    private String token;
}
