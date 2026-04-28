package com.example.marketplace.infrastructure.dto;

import com.example.marketplace.domain.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PaymentRequest(
        @NotNull(message = "ID do pedido é obrigatório")
        UUID orderId,

        @NotNull(message = "Método de pagamento é obrigatório")
        PaymentMethod method,

        String cardToken,
        String pixKey
) {}
