package com.example.marketplace.domain.model;

import java.util.regex.Pattern;

public record Email(String value) {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public Email {

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }

        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Email inválido");
        }
    }
}
