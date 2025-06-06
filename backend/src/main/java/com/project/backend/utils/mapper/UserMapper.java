package com.project.backend.utils.mapper;

import com.project.backend.dto.auth.LoginDto;
import com.project.backend.dto.auth.RegisterDto;
import com.project.backend.entities.auth.User;

import java.util.UUID;

public class UserMapper {
    public User map(RegisterDto registerDto) {
        User user = new User();
        user.setFullName(registerDto.getFullName());
        user.setEmail(registerDto.getEmail());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setPassword(registerDto.getPassword());
        user.setId(UUID.randomUUID());
        return user;
    }

    public LoginDto map(User user) {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail(user.getEmail());
        loginDto.setPassword(user.getPassword());
        return loginDto;
    }
}
