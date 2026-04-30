package com.example.marketplace.infrastructure.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class OrderResponse {

    private UUID orderId;
    private BigDecimal total;
    private String status;
    private String pickupCode;
    private Integer itemCount;
    private String itemsSummary; // ex: "Hambúrguer, Cerveja e mais 2"
    private Instant createdAt;
}
