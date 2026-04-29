package com.example.marketplace.application;

import com.example.marketplace.domain.enums.OrderStatus;
import com.example.marketplace.domain.enums.PaymentMethod;
import com.example.marketplace.domain.model.Cart;
import com.example.marketplace.domain.model.CartItem;
import com.example.marketplace.domain.models.Order;
import com.example.marketplace.domain.models.OrderItem;
import com.example.marketplace.domain.models.Product;
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
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final FindCartService findCartService;
    private final UpdateCartService updateCartService;
    private final FindProductService findProductService;
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

        // 2. Buscar dados dos produtos (nome, imagem) para snapshot no pedido
        List<UUID> productIds = selectedItems.stream()
                .map(CartItem::getProductId)
                .toList();

        Map<UUID, Product> productMap = findProductService.getProductByIdIn(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        // 3. Criar order com snapshot dos produtos
        List<OrderItem> orderItems = selectedItems.stream()
                .map(i -> {
                    Product product = productMap.get(i.getProductId());
                    String name = product != null ? product.getName() : "Produto removido";
                    String imgUrl = product != null ? product.getImgUrl() : null;
                    return new OrderItem(i.getProductId(), name, imgUrl, i.getQuantity(), i.getPriceAtMoment());
                })
                .toList();

        // 4. Gerar pickupCode ANTES de salvar o pedido (persistido junto)
        String pickupCode = generatePickupCode();

        Order order = new Order(userId, orderItems, OrderStatus.PENDING, pickupCode);
        var savedOrder = orderMapper.toOrder(orderRepositoryJpa.createOrder(order));

        BigDecimal totalAmount = savedOrder.getTotal();

        try {
            // 5. Decrementar estoque
            for (CartItem item : selectedItems) {
                stockService.decrementStock(item.getProductId(), item.getQuantity());
            }

            // 6. Processar pagamento
            PaymentResponse paymentResponse;

            if (request.method() == PaymentMethod.WALLET) {
                walletService.debit(userId, totalAmount,
                        "Pedido #" + savedOrder.getId().toString().substring(0, 8),
                        savedOrder.getId());
                paymentResponse = paymentService.processPayment(
                        userId, savedOrder.getId(), totalAmount,
                        PaymentMethod.WALLET, null, null);
            } else {
                paymentResponse = paymentService.processPayment(
                        userId, savedOrder.getId(), totalAmount,
                        request.method(), request.cardToken(), request.pixKey());
            }

            // 7. Remover itens do carrinho
            selectedItems.forEach(i -> cart.removeItem(i.getId()));
            updateCartService.updateCart(cart);

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
            log.error("Erro no checkout orderId={}: {}", savedOrder.getId(), e.getMessage());
            throw e;
        }
    }

    /**
     * Gera código de retirada único e legível para o torcedor.
     * Formato: PICKUP-XXXXXXXX (8 chars alfanuméricos uppercase)
     */
    private String generatePickupCode() {
        return "PICKUP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
