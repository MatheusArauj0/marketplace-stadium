package com.example.marketplace.infrastructure.repository;

import com.example.marketplace.infrastructure.entities.CartEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, UUID> {

    Optional<CartEntity> findByUserId(UUID userId);

    @EntityGraph(attributePaths = {"items"})
    Page<CartEntity> findAll(Pageable pageable);

    @Query("SELECT c FROM CartEntity c LEFT JOIN c.items i WHERE i.id = :itemId AND c.userId = :userId")
    Optional<CartEntity> findByItemId(@Param("itemId") UUID itemId, @Param("userId") UUID userId);
}
