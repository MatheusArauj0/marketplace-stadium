package com.example.marketplace.infrastructure.repository.jpa;

import com.example.marketplace.infrastructure.entities.CartItemEntity;
import com.example.marketplace.infrastructure.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CartItemRepositoryJpa {
    private final CartItemRepository cartItemRepository;

    public Optional<CartItemEntity> findByCartItemId(UUID cartItemId) {
        return cartItemRepository.findById(cartItemId);
    }

    public CartItemEntity updateCartItem(CartItemEntity cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public void deleteCartItem(UUID cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
    public void deleteAllItemByUserId(UUID userId) {
        cartItemRepository.deleteAllByCart_Id(userId);
    }

    
}
