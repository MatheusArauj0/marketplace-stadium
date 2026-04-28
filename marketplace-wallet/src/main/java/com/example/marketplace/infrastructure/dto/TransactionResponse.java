package com.example.marketplace.infrastructure.dto;

import com.example.marketplace.domain.enums.TransactionType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class TransactionResponse {

    private UUID id;
    private TransactionType type;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    private String description;
    private UUID referenceId;
    private Instant createdAt;
}
