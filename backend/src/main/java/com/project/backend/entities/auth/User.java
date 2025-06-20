package com.project.backend.entities.auth;

import com.project.backend.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name="image")
    private String image = null;
    @Column(name = "fullName")
    private String fullName;
    @Column(name="email")
    @Email(message = "Incorrect email format")
    private String email;
    @Column(name="phoneNumber", nullable = true)
    private String phoneNumber = null;
    @Column(name="password")
    @Size(min = 6, message = "Min password length must be over than 6 symbol")
    private String password;
    @Column(name="role")
    private Role role = Role.CLIENT;
    @Column(name = "createTime")
    private LocalDateTime createTime = LocalDateTime.now();

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return id.toString();
    }
}
