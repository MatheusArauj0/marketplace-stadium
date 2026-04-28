package com.example.marketplace.application;

import com.example.marketplace.domain.model.Cart;
import com.example.marketplace.infrastructure.mapper.CartMapper;
import com.example.marketplace.infrastructure.repository.jpa.CartRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateCartService {
    private final CartRepositoryJpa cartRepositoryJpa;
    private final CartMapper cartMapper;

    @Transactional
    public Cart createCart(UUID userId) {
        return cartMapper.toCart(cartRepositoryJpa.createCart(userId));
    }
}
