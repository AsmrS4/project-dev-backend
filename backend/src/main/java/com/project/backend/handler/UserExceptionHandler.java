package com.project.backend.handler;


import com.project.backend.exceptions.BadRequestException;
import com.project.backend.exceptions.LoginFailedException;
import com.project.backend.exceptions.NotUniqueException;
import com.project.backend.exceptions.UnauthorizedException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Hidden
@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    ResponseEntity<Map<String, Object>> handleUserNotFoundException(UsernameNotFoundException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("status: ", HttpStatus.NOT_FOUND.value());
        errors.put("error: ", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\":" + ex.getMessage() + " \"code\": \"" + HttpStatus.UNAUTHORIZED.value() + "\"}");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotUniqueException.class)
    ResponseEntity<Map<String, Object>>  handleNotUniqueException(NotUniqueException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("status: ", HttpStatus.BAD_REQUEST.value());
        errors.put("error: ", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(LoginFailedException.class)
    ResponseEntity<Map<String, Object>>  handleLoginFailedException(LoginFailedException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("status: ", HttpStatus.BAD_REQUEST.value());
        errors.put("error: ", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<Map<String, Object>>  handleBadRequestException(BadRequestException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("status: ", HttpStatus.BAD_REQUEST.value());
        errors.put("error: ", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        response.put("status:", HttpStatus.BAD_REQUEST.value());
        response.put("errors", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
