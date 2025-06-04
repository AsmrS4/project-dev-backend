package com.project.backend.utils.mapper;

import com.project.backend.dto.auth.RegisterDto;
import com.project.backend.entities.auth.User;

public class UserMapper {
    public User map(RegisterDto registerDto) {
        User user = new User();
        user.setFullName(registerDto.getFullName());
        user.setEmail(registerDto.getEmail());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setPassword(registerDto.getPassword());

        return user;
    }
}
