package com.project.backend.controllers;

import com.project.backend.dto.auth.LoginDto;
import com.project.backend.dto.auth.RegisterDto;
import com.project.backend.services.auth.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@Tag(name = "Authorization", description = "Эндпоинты для регистрации и авторизации пользователей")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }
    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDto registerDto){
        return ResponseEntity.ok(authService.register(registerDto));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout() throws AuthException {
        return ResponseEntity.ok(authService.logout());
    }
}
