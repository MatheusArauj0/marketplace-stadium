package com.example.marketplace.infrastructure.controller;

import com.example.marketplace.application.CategoryService;
import com.example.marketplace.infrastructure.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<List<CategoryResponse>> getAllProducts() {
    return ResponseEntity.ok(categoryService.getAllProducts());
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<CategoryResponse> getProductById(@PathVariable("id") UUID id) {
    return ResponseEntity.ok(categoryService.getProductById(id));
  }
}
