package com.example.marketplace.infrastructure.repository;

import com.example.marketplace.infrastructure.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    List<OrderEntity> findByUserIdOrderByCreatedAtDesc(UUID userId);

    Optional<OrderEntity> findByIdAndUserId(UUID id, UUID userId);

    Optional<OrderEntity> findByPickupCode(String pickupCode);
}
