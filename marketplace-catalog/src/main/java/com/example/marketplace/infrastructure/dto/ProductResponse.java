package com.example.marketplace.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

  private UUID id;
  private String name;
  private String description;

  private BigDecimal price;
  private String imgUrl;
  private String qrCode;
  private Long quantidade;
  private Instant date;
  private Set<CategoryResponse> categories = new HashSet<>();
}
