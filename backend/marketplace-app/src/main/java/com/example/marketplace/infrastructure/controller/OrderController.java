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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Pedidos", description = "Checkout, consulta de pedidos e pagamentos do torcedor")
@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final CheckoutService checkoutService;
    private final OrderQueryService orderQueryService;
    private final PaymentService paymentService;

    @Operation(summary = "Realizar checkout",
            description = "Finaliza a compra: debita da wallet, decrementa estoque, gera código de retirada (pickup code).")
    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponse> checkout(@Valid @RequestBody CheckoutRequest request) {
        return ResponseEntity.ok(
                checkoutService.checkout(SecurityUtils.getAuthenticatedUserId(), request));
    }

    @Operation(summary = "Meus pedidos",
            description = "Lista todos os pedidos do torcedor autenticado com resumo e pickup code para QR code.")
    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponse>> getMyOrders() {
        return ResponseEntity.ok(
                orderQueryService.getMyOrders(SecurityUtils.getAuthenticatedUserId()));
    }

    @Operation(summary = "Detalhe do pedido",
            description = "Retorna os itens comprados, preços, status e pickup code do pedido.")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(
            @Parameter(description = "ID do pedido") @PathVariable UUID orderId) {
        return ResponseEntity.ok(
                orderQueryService.getOrderDetail(orderId, SecurityUtils.getAuthenticatedUserId()));
    }

    @Operation(summary = "Meus pagamentos",
            description = "Lista todos os pagamentos realizados pelo torcedor autenticado.")
    @GetMapping("/payments")
    public ResponseEntity<List<PaymentResponse>> getMyPayments() {
        return ResponseEntity.ok(
                paymentService.getByUserId(SecurityUtils.getAuthenticatedUserId()));
    }

    @Operation(summary = "Pagamento por pedido",
            description = "Retorna o detalhe do pagamento vinculado a um pedido específico.")
    @GetMapping("/payments/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentByOrder(
            @Parameter(description = "ID do pedido") @PathVariable UUID orderId) {
        return ResponseEntity.ok(paymentService.getByOrderId(orderId));
    }
}
