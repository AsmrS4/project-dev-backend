package com.project.backend.services.auth;

import com.project.backend.dto.auth.LoginDto;
import com.project.backend.dto.auth.RegisterDto;

public interface AuthService {
    Object login(LoginDto loginDto);
    Object register(RegisterDto registerDto);
    Object logout();
}
