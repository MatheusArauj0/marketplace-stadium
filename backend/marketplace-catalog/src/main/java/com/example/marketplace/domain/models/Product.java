package com.example.marketplace.domain.models;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Product implements Serializable {
  private static final long serialVersionUID = 1L;

  private UUID id;
  private String name;
  private String description;

  private BigDecimal price;
  private String imgUrl;
  private String qrCode;
  private Integer quantidade;
  private Instant date;
  private Set<Category> categories = new HashSet<>();
}
