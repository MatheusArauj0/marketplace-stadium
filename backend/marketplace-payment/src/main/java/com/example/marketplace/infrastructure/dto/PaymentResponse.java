package com.example.marketplace.infrastructure.dto;

import com.example.marketplace.domain.enums.PaymentMethod;
import com.example.marketplace.domain.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class PaymentResponse {

    private UUID id;
    private UUID orderId;
    private UUID userId;
    private BigDecimal amount;
    private PaymentMethod method;
    private PaymentStatus status;
    private String gatewayTransactionId;
    private String qrCodePix;
    private Instant createdAt;
}
