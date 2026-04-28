package com.example.marketplace.application;

import com.example.marketplace.domain.model.Cart;
import com.example.marketplace.infrastructure.dto.AddCartItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddItemCartService {

    private final UUID userId = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1");

    private final FindProductService findProductService;
    private final FindCartService findCartService;
    private final CreateCartService createCartService;
    private final UpdateCartService updateCartService;
    private final FindUserService userService;


    @Transactional
    public Cart addProductToCart(List<AddCartItemRequest> requestList) {

        var user = userService.findById(userId);

        var cart = findCartService
                .findCartByUserId(user.getId())
                .orElseGet(() -> createCartService.createCart(user.getId()));

        cart.setUserId(user.getId());

        for (var request : requestList) {

            var product = findProductService.getProductById(request.productId());

            validateStock(product.getQuantidade(), request.quantity());

            cart.addItem(product, request.quantity());
        }

        return updateCartService.updateCart(cart);

    }

    private void validateStock(Integer stock, Integer quantity) {
        if (stock < quantity) {
            throw new IllegalArgumentException("Not enough stock for the product");
        }
    }
}
