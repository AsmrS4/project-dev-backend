package com.project.backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Incorrect email format")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Min password length must be over than 6 symbol")
    private String password;
}
