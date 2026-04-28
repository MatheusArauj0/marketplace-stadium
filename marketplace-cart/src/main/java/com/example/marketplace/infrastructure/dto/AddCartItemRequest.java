package com.example.marketplace.infrastructure.dto;

import java.util.UUID;

public record AddCartItemRequest(UUID productId,
                                Integer quantity) {
}
