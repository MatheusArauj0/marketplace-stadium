package com.example.marketplace.application;

import com.example.marketplace.infrastructure.dto.CategoryResponse;
import com.example.marketplace.infrastructure.mapper.CategoryMapper;
import com.example.marketplace.infrastructure.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository repository;
  private final CategoryMapper mapper;

  public List<CategoryResponse> getAllProducts() {
    return repository.findAll().stream().map(mapper::toCategoryResponse).toList();
  }

  public CategoryResponse getProductById(UUID id) {
    return repository.findById(id).map(mapper::toCategoryResponse).orElseThrow(IllegalArgumentException::new);
  }
}
