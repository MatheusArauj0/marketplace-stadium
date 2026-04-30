package com.example.marketplace.application;

import com.example.marketplace.infrastructure.dto.UpdateCartItemRequest;
import com.example.marketplace.infrastructure.mapper.CartItemMapper;
import com.example.marketplace.infrastructure.repository.jpa.CartItemRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateCartItemService {

    private final CartItemMapper cartItemMapper;
    private final CartItemRepositoryJpa cartItemRepositoryJpa;


    @Transactional
    public void updateCartItem(UUID itemId, UpdateCartItemRequest request) {
        var cartSave = cartItemRepositoryJpa.findByCartItemId(itemId).orElseThrow(() -> new IllegalArgumentException("Cart item not found."));
        var carIteem = cartItemMapper.toCartItem(cartSave);
        carIteem.updateItem(request.quantity(), request.selected());

        var cartItemUpdated = cartItemMapper.toCartItemEntity(carIteem);
        cartItemUpdated.setCart(cartSave.getCart());

        cartItemMapper.toCartItem(cartItemRepositoryJpa.updateCartItem(cartItemUpdated));
    }

    @Transactional
    public void removeItem(UUID itemId) {
        cartItemRepositoryJpa.deleteCartItem(itemId);
    }
}
