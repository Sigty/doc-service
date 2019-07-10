package com.itacademy.service.exception;

public class DeleteException extends RuntimeException {

    public DeleteException() {
    }

    public DeleteException(String message) {
        super(message);
    }
}