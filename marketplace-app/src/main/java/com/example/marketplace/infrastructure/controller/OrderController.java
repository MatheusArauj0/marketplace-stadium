package com.example.marketplace.infrastructure.controller;

import com.example.marketplace.application.CreateOrderService;
import com.example.marketplace.domain.models.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderService createOrderService;


    @PostMapping("/checkout")

    public ResponseEntity<Order> createOrder() {
        return ResponseEntity.ok(createOrderService.checkout(UUID.randomUUID()));
    }
}
