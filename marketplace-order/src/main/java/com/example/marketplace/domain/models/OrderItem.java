package com.example.marketplace.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private UUID id;
    private UUID productId;
    private Integer quantity;
    private BigDecimal price;

    public OrderItem(UUID productId, Integer quantity, BigDecimal price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
