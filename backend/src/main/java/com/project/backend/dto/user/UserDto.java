package com.project.backend.dto.user;

import com.project.backend.enums.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String image;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Role role;
    private LocalDateTime createTime;
}
