package com.project.backend.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateDto {
    @NotBlank(message = "Укажите email")
    @Email(message = "Некорректный формат email")
    private String email;
    private String image;
    @NotBlank(message = "Укажите полное имя")
    private String fullName;
    private String phoneNumber;
}
