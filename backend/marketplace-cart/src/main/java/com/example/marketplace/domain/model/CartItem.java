package com.example.marketplace.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Data
@NoArgsConstructor
public class CartItem {
    private UUID id;
    private UUID productId;
    private Integer quantity;
    private BigDecimal priceAtMoment;
    private Boolean selected;

    public CartItem(UUID productId, Integer quantity, BigDecimal priceAtMoment) {
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtMoment = priceAtMoment;
    }

    public void updateItem(Integer quantity, Boolean selected) {
        if (nonNull(quantity)) {
            this.quantity = quantity;
        }
        if (nonNull(selected)) {
            this.selected = selected;
        }
    }

    public void increaseQuantity(Integer quantity) {
        this.quantity += quantity;
    }

    public boolean isSelected(){
        return this.selected;
    }
}
