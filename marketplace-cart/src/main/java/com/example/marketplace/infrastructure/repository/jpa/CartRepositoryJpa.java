package com.example.marketplace.infrastructure.repository.jpa;

import com.example.marketplace.infrastructure.entities.CartEntity;
import com.example.marketplace.infrastructure.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CartRepositoryJpa {
    private final CartRepository cartRepository;

    public Optional<CartEntity> findByUserId(UUID idUser) {
        return cartRepository.findByUserId(idUser);
    }

    public Page<CartEntity> findAll(Pageable pageable) {
        return cartRepository.findAll(pageable);
    }

    public Optional<CartEntity> findAllCarts(UUID idUser) {
        return cartRepository.findByUserId(idUser);
    }

    public CartEntity createCart(UUID idUser) {
        var newCart = new CartEntity();
        newCart.setUserId(idUser);
        return cartRepository.save(newCart);
    }

    public CartEntity updateCart(CartEntity entity) {
        return cartRepository.save(entity);
    }

}
