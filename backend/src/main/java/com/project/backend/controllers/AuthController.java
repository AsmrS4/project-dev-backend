package com.project.backend.controllers;

import com.project.backend.dto.auth.LoginDto;
import com.project.backend.dto.auth.RegisterDto;
import com.project.backend.services.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
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
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(authService.logout());
    }
}
