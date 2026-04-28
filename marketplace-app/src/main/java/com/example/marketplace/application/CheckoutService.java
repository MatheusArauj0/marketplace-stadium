package com.example.marketplace.application;

import com.example.marketplace.domain.enums.OrderStatus;
import com.example.marketplace.domain.enums.PaymentMethod;
import com.example.marketplace.domain.model.Cart;
import com.example.marketplace.domain.model.CartItem;
import com.example.marketplace.domain.models.Order;
import com.example.marketplace.domain.models.OrderItem;
import com.example.marketplace.infrastructure.dto.CheckoutRequest;
import com.example.marketplace.infrastructure.dto.CheckoutResponse;
import com.example.marketplace.infrastructure.dto.PaymentResponse;
import com.example.marketplace.infrastructure.mapper.OrderMapper;
import com.example.marketplace.infrastructure.repository.jpa.OrderRepositoryJpa;
import com.example.marketplace.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final FindCartService findCartService;
    private final UpdateCartService updateCartService;
    private final StockService stockService;
    private final WalletService walletService;
    private final PaymentService paymentService;
    private final OrderRepositoryJpa orderRepositoryJpa;
    private final OrderMapper orderMapper;

    @Transactional
    public CheckoutResponse checkout(UUID userId, CheckoutRequest request) {

        // 1. Buscar carrinho
        Cart cart = findCartService.findCartByUserId(userId)
                .orElseThrow(() -> new BusinessException("Carrinho não encontrado"));

        List<CartItem> selectedItems = cart.getItems().stream()
                .filter(CartItem::isSelected)
                .toList();

        if (selectedItems.isEmpty()) {
            throw new BusinessException("Nenhum item selecionado no carrinho");
        }

        // 2. Criar order
        List<OrderItem> orderItems = selectedItems.stream()
                .map(i -> new OrderItem(i.getProductId(), i.getQuantity(), i.getPriceAtMoment()))
                .toList();

        Order order = new Order(userId, orderItems, OrderStatus.PENDING);
        var savedOrder = orderMapper.toOrder(orderRepositoryJpa.createOrder(order));

        BigDecimal totalAmount = savedOrder.getTotal();

        try {
            // 3. Decrementar estoque
            for (CartItem item : selectedItems) {
                stockService.decrementStock(item.getProductId(), item.getQuantity());
            }

            // 4. Processar pagamento
            PaymentResponse paymentResponse;

            if (request.method() == PaymentMethod.WALLET) {
                // Debitar wallet
                walletService.debit(userId, totalAmount,
                        "Pedido #" + savedOrder.getId().toString().substring(0, 8),
                        savedOrder.getId());
                paymentResponse = paymentService.processPayment(
                        userId, savedOrder.getId(), totalAmount,
                        PaymentMethod.WALLET, null, null);
            } else {
                // Pagamento via gateway externo
                paymentResponse = paymentService.processPayment(
                        userId, savedOrder.getId(), totalAmount,
                        request.method(), request.cardToken(), request.pixKey());
            }

            // 5. Remover itens do carrinho
            selectedItems.forEach(i -> cart.removeItem(i.getId()));
            updateCartService.updateCart(cart);

            // 6. Gerar QR code de retirada
            String pickupCode = "PICKUP-" + savedOrder.getId().toString().substring(0, 8).toUpperCase();

            log.info("Checkout completo: orderId={}, userId={}, total=R${}, método={}, pickup={}",
                    savedOrder.getId(), userId, totalAmount, request.method(), pickupCode);

            return CheckoutResponse.builder()
                    .orderId(savedOrder.getId())
                    .total(totalAmount)
                    .paymentStatus(paymentResponse.getStatus().name())
                    .paymentMethod(paymentResponse.getMethod().name())
                    .pickupCode(pickupCode)
                    .message("Pedido realizado com sucesso! Apresente o código na retirada.")
                    .build();

        } catch (Exception e) {
            // Rollback: o @Transactional cuida do banco, mas logar o erro
            log.error("Erro no checkout orderId={}: {}", savedOrder.getId(), e.getMessage());
            throw e;
        }
    }
}
