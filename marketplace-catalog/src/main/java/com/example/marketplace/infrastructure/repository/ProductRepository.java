package com.example.marketplace.infrastructure.repository;

import com.example.marketplace.infrastructure.entities.ProductEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    @Override
    @EntityGraph(attributePaths = "categories")
    List<ProductEntity> findAll();

    @EntityGraph(attributePaths = "categories")
    Optional<ProductEntity> findById(UUID id);


    @EntityGraph(attributePaths = "categories")
    List<ProductEntity> findByIdIn(List<UUID> ids);
}
