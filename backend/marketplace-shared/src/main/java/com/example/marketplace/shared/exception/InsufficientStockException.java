package com.example.marketplace.shared.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class InsufficientStockException extends BusinessException {

    public InsufficientStockException(UUID productId, int requested, long available) {
        super("Estoque insuficiente para o produto " + productId
                + ": solicitado=" + requested + ", disponível=" + available,
                HttpStatus.CONFLICT);
    }
}
