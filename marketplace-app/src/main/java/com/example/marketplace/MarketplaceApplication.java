package com.example.marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.example.marketplace")
@EntityScan("com.example.marketplace.infrastructure.entities")
public class MarketplaceApplication {

  public static void main(String[] args) {
    SpringApplication.run(MarketplaceApplication.class, args);
  }
}
