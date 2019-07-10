package com.itacademy.service.exception;

public class InvalidAccessException extends RuntimeException {

    public InvalidAccessException() {
    }

    public InvalidAccessException(String message) {
        super(message);
    }
}
