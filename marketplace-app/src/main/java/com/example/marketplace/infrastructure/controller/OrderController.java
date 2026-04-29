package com.example.marketplace.infrastructure.controller;

import com.example.marketplace.application.CheckoutService;
import com.example.marketplace.application.OrderQueryService;
import com.example.marketplace.application.PaymentService;
import com.example.marketplace.infrastructure.dto.CheckoutRequest;
import com.example.marketplace.infrastructure.dto.CheckoutResponse;
import com.example.marketplace.infrastructure.dto.OrderDetailResponse;
import com.example.marketplace.infrastructure.dto.OrderResponse;
import com.example.marketplace.infrastructure.dto.PaymentResponse;
import com.example.marketplace.infrastructure.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final CheckoutService checkoutService;
    private final OrderQueryService orderQueryService;
    private final PaymentService paymentService;

    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponse> checkout(@Valid @RequestBody CheckoutRequest request) {
        return ResponseEntity.ok(
                checkoutService.checkout(SecurityUtils.getAuthenticatedUserId(), request));
    }

    /**
     * Lista todos os pedidos do torcedor (resumo com pickupCode).
     */
    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponse>> getMyOrders() {
        return ResponseEntity.ok(
                orderQueryService.getMyOrders(SecurityUtils.getAuthenticatedUserId()));
    }

    /**
     * Detalhe do pedido: produtos comprados, preços, pickupCode para QR code.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(@PathVariable UUID orderId) {
        return ResponseEntity.ok(
                orderQueryService.getOrderDetail(orderId, SecurityUtils.getAuthenticatedUserId()));
    }

    @GetMapping("/payments")
    public ResponseEntity<List<PaymentResponse>> getMyPayments() {
        return ResponseEntity.ok(
                paymentService.getByUserId(SecurityUtils.getAuthenticatedUserId()));
    }

    @GetMapping("/payments/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentByOrder(@PathVariable UUID orderId) {
        return ResponseEntity.ok(paymentService.getByOrderId(orderId));
    }
}
