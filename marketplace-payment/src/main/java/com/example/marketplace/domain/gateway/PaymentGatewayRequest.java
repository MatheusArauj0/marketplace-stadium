package com.example.marketplace.domain.gateway;

import com.example.marketplace.domain.enums.PaymentMethod;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class PaymentGatewayRequest {

    private UUID orderId;
    private UUID userId;
    private BigDecimal amount;
    private PaymentMethod method;
    private String cardToken;       // token do cartão (quando aplicável)
    private String pixKey;          // chave PIX (quando aplicável)
    private String description;
}
