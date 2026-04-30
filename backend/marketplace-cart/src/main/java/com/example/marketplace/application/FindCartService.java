package com.example.marketplace.application;

import com.example.marketplace.domain.model.Cart;
import com.example.marketplace.infrastructure.mapper.CartMapper;
import com.example.marketplace.infrastructure.repository.jpa.CartRepositoryJpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindCartService {
    private final CartRepositoryJpa repositoryJpa;
    private final CartMapper mapper;

    public Optional<Cart> findCartByUserId(UUID userId) {
        log.debug("Buscando carrinho do usuário {}", userId);
        return repositoryJpa.findByUserId(userId).map(mapper::toCart);
    }

    public Page<Cart> findCartByUserIdPaged(UUID userId, Pageable pageable) {
        log.debug("Buscando carrinho paginado do usuário {}", userId);
        return repositoryJpa.findByUserId(userId)
                .map(entity -> {
                    Cart cart = mapper.toCart(entity);
                    return new PageImpl<>(Collections.singletonList(cart), pageable, 1);
                })
                .orElse(new PageImpl<>(Collections.emptyList(), pageable, 0));
    }

    public Page<Cart> findAllCarts(Pageable pageable) {
        return repositoryJpa.findAll(pageable).map(mapper::toCart);
    }
}
