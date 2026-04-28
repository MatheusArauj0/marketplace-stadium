package com.example.marketplace.application;

import com.example.marketplace.infrastructure.mapper.CartItemMapper;
import com.example.marketplace.infrastructure.repository.jpa.CartItemRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteCartItemService {

    private final CartItemMapper cartItemMapper;
    private final CartItemRepositoryJpa cartItemRepositoryJpa;

    public void removeItem(UUID itemId) {
        cartItemRepositoryJpa.deleteCartItem(itemId);
    }

    @Transactional
    public void removeAllItens(UUID cartId) {
        cartItemRepositoryJpa.deleteAllItemByUserId(cartId);
    }
}
