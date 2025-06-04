package com.project.backend.entities.auth;

import com.project.backend.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "fullName")
    private String fullName;
    @Column(name="email")
    @Email
    private String email;
    @Column(name="phoneNumber", nullable = true)
    private String phoneNumber = null;
    @Column(name="password")
    private String password;
    @Column(name="role")
    private Role role = Role.CLIENT;
    @Column(name = "createTime")
    private LocalDateTime createTime = LocalDateTime.now();
}
