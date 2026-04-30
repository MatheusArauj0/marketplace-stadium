package com.example.marketplace.infrastructure.repository;

import com.example.marketplace.infrastructure.entities.ProductEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    @Override
    @EntityGraph(attributePaths = "categories")
    List<ProductEntity> findAll();

    @EntityGraph(attributePaths = "categories")
    Page<ProductEntity> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "categories")
    Optional<ProductEntity> findById(UUID id);

    @EntityGraph(attributePaths = "categories")
    List<ProductEntity> findByIdIn(List<UUID> ids);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductEntity p WHERE p.id = :id")
    Optional<ProductEntity> findByIdForUpdate(UUID id);

    @Modifying
    @Query("UPDATE ProductEntity p SET p.quantidade = p.quantidade - :qty WHERE p.id = :id AND p.quantidade >= :qty")
    int decrementStock(UUID id, long qty);
}
