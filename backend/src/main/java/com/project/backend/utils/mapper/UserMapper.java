package com.project.backend.utils.mapper;

import com.project.backend.dto.auth.LoginDto;
import com.project.backend.dto.auth.RegisterDto;
import com.project.backend.dto.user.UserDto;
import com.project.backend.dto.user.UserUpdateDto;
import com.project.backend.entities.auth.User;
import com.project.backend.enums.Role;

import java.util.UUID;

public class UserMapper {
    public User map(RegisterDto registerDto) {
        User user = new User();
        user.setFullName(registerDto.getFullName());
        user.setEmail(registerDto.getEmail());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setPassword(registerDto.getPassword());
        user.setId(UUID.randomUUID());
        user.setImage(null);
        return user;
    }

    public LoginDto map(User user) {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail(user.getEmail());
        loginDto.setPassword(user.getPassword());
        return loginDto;
    }

    public User map( User user, UserUpdateDto updateDto) {
        user.setFullName(updateDto.getFullName());
        user.setEmail(updateDto.getEmail());
        user.setPhoneNumber(updateDto.getPhoneNumber());
        user.setImage(updateDto.getImage());
        return user;
    }

    public UserDto mapUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setRole(user.getRole());
        userDto.setCreateTime(user.getCreateTime());
        userDto.setImage(user.getImage());

        return userDto;
    }
    private Role roleMapper(int type){
        return switch (type) {
            case 0 -> Role.CLIENT;
            case 1 -> Role.MANAGER;
            case 2 -> Role.SECURITY;
            default -> null;
        };
    }
}
