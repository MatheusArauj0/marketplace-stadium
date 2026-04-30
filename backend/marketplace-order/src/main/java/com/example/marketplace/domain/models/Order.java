package com.example.marketplace.domain.models;

import com.example.marketplace.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private UUID id;
    private UUID userId;
    private List<OrderItem> items;
    private BigDecimal total;
    private OrderStatus status;
    private String pickupCode;

    public Order(UUID userId, List<OrderItem> items, OrderStatus orderStatus) {
        this.userId = userId;
        this.items = items;
        this.total = calculateTotal(items);
        this.status = orderStatus;
    }

    public Order(UUID userId, List<OrderItem> items, OrderStatus orderStatus, String pickupCode) {
        this.userId = userId;
        this.items = items;
        this.total = calculateTotal(items);
        this.status = orderStatus;
        this.pickupCode = pickupCode;
    }

    public BigDecimal calculateTotal(List<OrderItem> items) {
        return items.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
