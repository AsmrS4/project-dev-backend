package com.project.backend.dto.auth;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegisterDto {
    @NotBlank(message = "Укажите полное имя")
    private String fullName;
    @NotBlank(message = "Укажите email")
    @Email(message = "Некорректный формат email")
    private String email;
    @Nullable
    private String phoneNumber = null;
    @NotBlank(message = "Укажите пароль")
    @Size(min = 6, message = "Длина пароля должна быть больше 6 символов")
    private String password;
}
