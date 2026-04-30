package com.example.marketplace.application;

import com.example.marketplace.domain.enums.OrderStatus;
import com.example.marketplace.domain.model.Cart;
import com.example.marketplace.domain.model.CartItem;
import com.example.marketplace.domain.models.Order;
import com.example.marketplace.domain.models.OrderItem;
import com.example.marketplace.infrastructure.mapper.OrderMapper;
import com.example.marketplace.infrastructure.repository.jpa.OrderRepositoryJpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateOrderService {

    private final FindCartService findCartService;
    private final UpdateCartService updateCartService;
    private final OrderRepositoryJpa orderRepositoryJpa;
    private final OrderMapper orderMapper;

    @Transactional
    public Order checkout(UUID userId) {
        log.info("Iniciando checkout para usuário {}", userId);
        Cart cart = findCartService.findCartByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Carrinho não encontrado para o usuário: " + userId));

        List<CartItem> selectedItems = cart.getItems().stream()
                .filter(CartItem::isSelected)
                .toList();

        if (selectedItems.isEmpty()) {
            log.warn("Nenhum item selecionado no carrinho do usuário {}", userId);
            throw new IllegalArgumentException("Nenhum item selecionado no carrinho");
        }

        log.info("Checkout com {} itens selecionados para usuário {}", selectedItems.size(), userId);
        Order order = createFromCart(cart.getUserId(), selectedItems);

        var savedOrder = orderMapper.toOrder(orderRepositoryJpa.createOrder(order));
        log.info("Pedido {} criado com sucesso para usuário {}", savedOrder.getId(), userId);

        selectedItems.forEach(i -> cart.removeItem(i.getId()));
        updateCartService.updateCart(cart);

        return savedOrder;
    }

    private Order createFromCart(UUID userId, List<CartItem> selectedItems) {
        List<OrderItem> items = selectedItems.stream()
                .map(i -> new OrderItem(
                        i.getProductId(),
                        i.getQuantity(),
                        i.getPriceAtMoment()
                ))
                .toList();

        return new Order(userId, items, OrderStatus.PENDING);
    }
}
