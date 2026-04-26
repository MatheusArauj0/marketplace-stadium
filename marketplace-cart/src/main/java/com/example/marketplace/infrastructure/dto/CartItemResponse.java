package com.example.marketplace.infrastructure.dto;

import com.example.marketplace.domain.model.CartItem;

import java.util.List;
import java.util.UUID;

public record CartItemResponse(UUID id,
                               UUID userId, List<CartItem> items) {
}