package com.example.marketplace.shared.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BusinessException {

    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException() {
        super("Acesso não autorizado", HttpStatus.UNAUTHORIZED);
    }
}
