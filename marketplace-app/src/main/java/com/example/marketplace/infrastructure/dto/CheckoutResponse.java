package com.example.marketplace.infrastructure.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class CheckoutResponse {

    private UUID orderId;
    private BigDecimal total;
    private String paymentStatus;
    private String paymentMethod;
    private String pickupCode;
    private String message;
}
