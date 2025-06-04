package com.project.backend.exceptions;

public class NotUniqueException extends RuntimeException{
    public NotUniqueException(String message) {
        super(message);
    }
}
