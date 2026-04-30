package com.example.marketplace.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank(message = "Nome da categoria é obrigatório")
        String name
) {}
