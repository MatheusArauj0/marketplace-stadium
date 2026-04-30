package com.example.marketplace.application;

import com.example.marketplace.infrastructure.dto.OrderDetailResponse;
import com.example.marketplace.infrastructure.dto.OrderItemResponse;
import com.example.marketplace.infrastructure.dto.OrderResponse;
import com.example.marketplace.infrastructure.dto.PaymentResponse;
import com.example.marketplace.infrastructure.entities.OrderEntity;
import com.example.marketplace.infrastructure.entities.OrderItemEntity;
import com.example.marketplace.infrastructure.repository.jpa.OrderRepositoryJpa;
import com.example.marketplace.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepositoryJpa orderRepositoryJpa;
    private final PaymentService paymentService;

    @Transactional(readOnly = true)
    public List<OrderResponse> getMyOrders(UUID userId) {
        log.info("Consultando pedidos do usuário {}", userId);
        List<OrderEntity> orders = orderRepositoryJpa.findByUserId(userId);
        log.debug("Encontrados {} pedidos para o usuário {}", orders.size(), userId);

        return orders.stream()
                .map(this::toOrderResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderDetailResponse getOrderDetail(UUID orderId, UUID userId) {
        log.info("Consultando detalhe do pedido {} para usuário {}", orderId, userId);
        OrderEntity order = orderRepositoryJpa.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        PaymentResponse payment = null;
        try {
            payment = paymentService.getByOrderId(orderId);
        } catch (Exception ignored) {
            // Pagamento pode não existir ainda se o pedido está pendente
        }

        return toOrderDetailResponse(order, payment);
    }

    private OrderResponse toOrderResponse(OrderEntity order) {
        List<OrderItemEntity> items = order.getItems();
        int itemCount = items.size();
        String summary = buildItemsSummary(items);

        return OrderResponse.builder()
                .orderId(order.getId())
                .total(order.getTotal())
                .status(order.getStatus().name())
                .pickupCode(order.getPickupCode())
                .itemCount(itemCount)
                .itemsSummary(summary)
                .createdAt(order.getCreatedAt())
                .build();
    }

    private OrderDetailResponse toOrderDetailResponse(OrderEntity order, PaymentResponse payment) {
        List<OrderItemResponse> items = order.getItems().stream()
                .map(this::toOrderItemResponse)
                .toList();

        return OrderDetailResponse.builder()
                .orderId(order.getId())
                .total(order.getTotal())
                .status(order.getStatus().name())
                .pickupCode(order.getPickupCode())
                .paymentMethod(payment != null ? payment.getMethod().name() : null)
                .paymentStatus(payment != null ? payment.getStatus().name() : null)
                .items(items)
                .createdAt(order.getCreatedAt())
                .build();
    }

    private OrderItemResponse toOrderItemResponse(OrderItemEntity item) {
        BigDecimal subtotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

        return OrderItemResponse.builder()
                .productId(item.getProductId())
                .productName(item.getProductName())
                .productImgUrl(item.getProductImgUrl())
                .quantity(item.getQuantity())
                .unitPrice(item.getPrice())
                .subtotal(subtotal)
                .build();
    }

    /**
     * Gera resumo dos itens: "Hambúrguer, Cerveja e mais 2"
     */
    private String buildItemsSummary(List<OrderItemEntity> items) {
        if (items.isEmpty()) return "";

        if (items.size() == 1) {
            return items.get(0).getProductName();
        }

        if (items.size() == 2) {
            return items.get(0).getProductName() + " e " + items.get(1).getProductName();
        }

        // 3+: "Hambúrguer, Cerveja e mais 2"
        return items.get(0).getProductName() + ", " + items.get(1).getProductName()
                + " e mais " + (items.size() - 2);
    }
}
