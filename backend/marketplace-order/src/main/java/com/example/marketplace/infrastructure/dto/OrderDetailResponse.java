package com.example.marketplace.infrastructure.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class OrderDetailResponse {

    private UUID orderId;
    private BigDecimal total;
    private String status;
    private String pickupCode;
    private String paymentMethod;
    private String paymentStatus;
    private List<OrderItemResponse> items;
    private Instant createdAt;
}
