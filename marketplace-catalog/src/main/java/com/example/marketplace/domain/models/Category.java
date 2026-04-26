package com.example.marketplace.domain.models;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class Category {

  private UUID id;
  private String name;

  private Instant createdAt;

  private Instant updatedAt;
}
