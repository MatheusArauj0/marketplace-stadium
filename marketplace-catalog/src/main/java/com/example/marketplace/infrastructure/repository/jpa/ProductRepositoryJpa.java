package com.example.marketplace.infrastructure.repository.jpa;

import com.example.marketplace.domain.models.Product;
import com.example.marketplace.infrastructure.mapper.ProductMapper;
import com.example.marketplace.infrastructure.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryJpa {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<Product> findAll() {
        return productRepository.findAll().stream().map(productMapper::toProduct).toList();
    }

    public List<Product> findByIdIn(List<UUID> idProduct) {
        return productRepository.findByIdIn(idProduct).stream().map(productMapper::toProduct).toList();
    }

    public Product findById(UUID idProduct) {
        var productEntity = productRepository.findById(idProduct).orElseThrow(() -> new IllegalArgumentException("Entity not found"));

        return productMapper.toProduct(productEntity);
    }
}
