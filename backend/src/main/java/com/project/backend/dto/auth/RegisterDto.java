package com.project.backend.dto.auth;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class RegisterDto {
    @NotBlank(message = "FullName is required")
    private String fullName;
    @NotBlank(message = "Email is required")
    @Email(message = "Incorrect email format")
    private String email;
    @Nullable
    private String phoneNumber;
    @NotBlank(message = "Password is required")
    @Min(value = 6, message = "Min password length must be over than 6 symbol")
    private String password;
}
