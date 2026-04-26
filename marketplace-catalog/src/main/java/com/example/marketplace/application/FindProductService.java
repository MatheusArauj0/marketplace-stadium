package com.example.marketplace.application;

import com.example.marketplace.domain.models.Product;
import com.example.marketplace.infrastructure.mapper.ProductMapper;
import com.example.marketplace.infrastructure.repository.jpa.ProductRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindProductService {

  private final ProductRepositoryJpa repository;
  private final ProductMapper mapper;

public List<Product> getAllProducts() {
    return repository.findAll();
  }

  public List<Product> getProductByIdIn(List<UUID> id) {
    return repository.findByIdIn(id);
  }

  public Product getProductById(UUID id) {
    return repository.findById(id);
  }
}
