package com.project.backend.services.auth;

import com.project.backend.dto.auth.LoginDto;
import com.project.backend.dto.auth.RegisterDto;
import jakarta.security.auth.message.AuthException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    Object login(LoginDto loginDto);
    Object register(RegisterDto registerDto);
    Object logout() throws AuthException;
}
