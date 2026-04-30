package com.example.marketplace.application;

import com.example.marketplace.domain.models.Product;
import com.example.marketplace.infrastructure.mapper.ProductMapper;
import com.example.marketplace.infrastructure.repository.jpa.ProductRepositoryJpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindProductService {

    private final ProductRepositoryJpa repository;
    private final ProductMapper mapper;

    public List<Product> getAllProducts() {
        log.debug("Listando todos os produtos");
        return repository.findAll();
    }

    public List<Product> getProductByIdIn(List<UUID> id) {
        log.debug("Buscando produtos por IDs: {}", id);
        return repository.findByIdIn(id);
    }

    public Product getProductById(UUID id) {
        log.debug("Buscando produto ID={}", id);
        return repository.findById(id);
    }
}
