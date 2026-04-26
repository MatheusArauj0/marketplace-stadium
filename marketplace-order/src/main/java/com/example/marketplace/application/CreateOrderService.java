package com.example.marketplace.application;

import com.example.marketplace.domain.enums.OrderStatus;
import com.example.marketplace.domain.model.Cart;
import com.example.marketplace.domain.model.CartItem;
import com.example.marketplace.domain.models.Order;
import com.example.marketplace.domain.models.OrderItem;
import com.example.marketplace.infrastructure.mapper.OrderMapper;
import com.example.marketplace.infrastructure.repoitory.jpa.OrderRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateOrderService {

    private final FindCartService findCartService;
    private final UpdateCartService updateCartService;
    private final OrderRepositoryJpa orderRepositoryJpa;
    private final OrderMapper orderMapper;


    @Transactional
    public Order checkout(UUID userId) {
        Cart cart = findCartService.findCartByUserId(userId).orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + userId));

        Order order = createFromCart(cart);

        var orderResponse = orderMapper.toOrder(orderRepositoryJpa.createOrder(order));

        var itensSelectedToRemove = itensToRemoveFromcart(cart);
        itensSelectedToRemove.forEach(i -> cart.removeItem(i.getId()));

        updateCartService.updateCart(cart);
        return orderResponse;
    }

    private Order createFromCart(Cart cart) {
        List<OrderItem> items = cart.getItems()
                .stream()
                .filter(CartItem::isSelected)
                .map(i -> new OrderItem(
                        i.getProductId(),
                        i.getQuantity(),
                        i.getPriceAtMoment()
                ))
                .toList();

        return new Order(cart.getUserId(), items, OrderStatus.PENDING);
    }

    private List<CartItem> itensToRemoveFromcart(Cart cart) {
        return cart.getItems()
                .stream()
                .filter(CartItem::isSelected)
                .toList();
    }
}
