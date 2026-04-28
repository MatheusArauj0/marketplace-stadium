package com.example.marketplace.shared.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends BusinessException {

    public DuplicateResourceException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public DuplicateResourceException(String resource, String field, Object value) {
        super(resource + " já existe com " + field + ": " + value, HttpStatus.CONFLICT);
    }
}
