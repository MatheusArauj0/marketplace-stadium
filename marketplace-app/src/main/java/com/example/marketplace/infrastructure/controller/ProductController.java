package com.example.marketplace.infrastructure.controller;

import com.example.marketplace.application.FindProductService;
import com.example.marketplace.infrastructure.dto.ProductResponse;
import com.example.marketplace.infrastructure.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final FindProductService findProductService;
  private final ProductMapper mapper;

  @GetMapping
  public ResponseEntity<List<ProductResponse>> getAllProducts() {

    return ResponseEntity.ok(findProductService.getAllProducts().stream().map(mapper::toProductResponse).toList());
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") UUID id) {
    return ResponseEntity.ok(mapper.toProductResponse(findProductService.getProductById(id)));
  }
}
