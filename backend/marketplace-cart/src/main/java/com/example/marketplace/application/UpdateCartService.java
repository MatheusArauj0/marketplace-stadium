package com.example.marketplace.application;

import com.example.marketplace.domain.model.Cart;
import com.example.marketplace.infrastructure.mapper.CartMapper;
import com.example.marketplace.infrastructure.repository.jpa.CartRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCartService {

    private final CartRepositoryJpa cartRepositoryJpa;
    private final CartMapper cartMapper;


    @Transactional
    public Cart updateCart(Cart cart) {
        return cartMapper.toCart(cartRepositoryJpa.updateCart(cartMapper.toCartEntity(cart)));
    }
}
