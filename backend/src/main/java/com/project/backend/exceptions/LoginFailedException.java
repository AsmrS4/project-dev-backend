package com.project.backend.exceptions;

public class LoginFailedException extends RuntimeException{
    public LoginFailedException(String message) {
        super(message);
    }
}
