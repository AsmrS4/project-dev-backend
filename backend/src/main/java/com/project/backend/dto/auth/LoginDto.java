package com.project.backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginDto {
    @NotBlank(message = "Укажите email")
    @Email(message = "Некорректный формат email")
    private String email;
    @NotBlank(message = "Укажите пароль")
    @Size(min = 6, message = "Длина пароля должна быть больше 6 символов")
    private String password;
}
