package com.example.marketplace.shared.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends BusinessException {

    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }

    public ForbiddenException() {
        super("Acesso negado — permissão insuficiente", HttpStatus.FORBIDDEN);
    }
}
