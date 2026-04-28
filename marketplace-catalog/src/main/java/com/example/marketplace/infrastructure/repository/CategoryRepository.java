package com.example.marketplace.infrastructure.repository;

import com.example.marketplace.infrastructure.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {

 /*   @Override
    @EntityGraph(attributePaths = "categories")
    List<CategoryEntity> findAll();

    @Override
    @EntityGraph(attributePaths = "categories")
    Optional<CategoryEntity> findById(UUID id);*/
}
