package com.example.marketplace.application;

import com.example.marketplace.domain.model.Cart;
import com.example.marketplace.infrastructure.mapper.CartMapper;
import com.example.marketplace.infrastructure.repository.jpa.CartRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindCartService {
    private final CartRepositoryJpa repositoryJpa;
    private final CartMapper mapper;

    public Optional<Cart> findCartByUserId(UUID userId) {

        if (repositoryJpa.findByUserId(userId).isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(mapper.toCart(repositoryJpa.findByUserId(userId)
                .orElseThrow(() ->new IllegalArgumentException("Cart not found."))));
    }

    public Page<Cart> findAllCarts(Pageable pageable) {
        return repositoryJpa.findAll(pageable).map(mapper::toCart);

    }
}
