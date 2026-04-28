package com.example.marketplace.domain.model;

import com.example.marketplace.domain.models.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Cart {
    private UUID id;
    private UUID userId;
    private List<CartItem> items = new ArrayList<>();


    public void addItem(Product product, Integer quantity){
        var existingItem = items.stream()
                .filter(i -> i.getProductId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().increaseQuantity(quantity);
            return;
        }

        var cartItem = new CartItem(product.getId(), quantity, product.getPrice());

        items.add(cartItem);
    }

    public void updateItemQuantity(UUID itemId, Integer quantity) {
        var existingItem = items.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst();

        existingItem.ifPresent(cartItem -> cartItem.setQuantity(quantity));
    }

    public void removeItem(UUID itemId) {
        items.removeIf(i -> i.getId().equals(itemId));

    }

}
