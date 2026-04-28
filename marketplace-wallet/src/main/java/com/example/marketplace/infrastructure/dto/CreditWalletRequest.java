package com.example.marketplace.infrastructure.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreditWalletRequest(
        @NotNull(message = "Valor é obrigatório")
        @DecimalMin(value = "1.00", message = "Valor mínimo de recarga é R$ 1,00")
        BigDecimal amount
) {}
