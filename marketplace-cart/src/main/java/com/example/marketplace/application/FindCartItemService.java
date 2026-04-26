package com.example.marketplace.application;

import com.example.marketplace.domain.model.CartItem;
import com.example.marketplace.infrastructure.mapper.CartItemMapper;
import com.example.marketplace.infrastructure.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindCartItemService {
    private final CartItemRepository repositoryJpa;
    private final CartItemMapper mapper;


    public Optional<CartItem> findByItemCartId(UUID cartItemId) {
        return repositoryJpa.findById(cartItemId).map(mapper::toCartItem);

    }
}
