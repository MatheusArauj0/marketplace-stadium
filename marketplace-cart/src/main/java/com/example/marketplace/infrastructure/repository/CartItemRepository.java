package com.example.marketplace.infrastructure.repository;

import com.example.marketplace.infrastructure.entities.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, UUID> {

    void deleteAllByCart_Id(UUID cartId);
}
