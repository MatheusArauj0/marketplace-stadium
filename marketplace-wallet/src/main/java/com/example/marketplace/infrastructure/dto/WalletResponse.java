package com.example.marketplace.infrastructure.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class WalletResponse {

    private UUID id;
    private UUID userId;
    private BigDecimal balance;
}
