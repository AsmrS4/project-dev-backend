package com.project.backend.controllers;

import com.project.backend.dto.user.UserDto;
import com.project.backend.dto.user.UserUpdateDto;
import com.project.backend.services.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "Эндпоинты для получения информации о пользователях")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid UserUpdateDto updateDto){
        return ResponseEntity.ok(userService.update(updateDto));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile(){
        return ResponseEntity.ok(userService.getUserProfile());
    }
}
