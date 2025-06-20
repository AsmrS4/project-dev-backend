package com.project.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserCardDto {
    private String fullName;
    private UUID id;
    private String image;
}
