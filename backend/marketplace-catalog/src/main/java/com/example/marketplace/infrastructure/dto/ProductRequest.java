package com.example.marketplace.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record ProductRequest(
        @NotBlank(message = "Nome do produto é obrigatório")
        String name,

        String description,

        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser positivo")
        BigDecimal price,

        String imgUrl,

        @NotNull(message = "Quantidade em estoque é obrigatória")
        @Positive(message = "Quantidade deve ser positiva")
        Long quantidade,

        Set<UUID> categoryIds
) {}
