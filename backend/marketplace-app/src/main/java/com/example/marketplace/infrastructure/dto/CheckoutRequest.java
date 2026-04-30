package com.example.marketplace.infrastructure.dto;

import com.example.marketplace.domain.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;

public record CheckoutRequest(
        @NotNull(message = "Método de pagamento é obrigatório")
        PaymentMethod method,

        String cardToken,
        String pixKey
) {}
