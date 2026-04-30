package com.example.marketplace.application;

import com.example.marketplace.domain.model.Cart;
import com.example.marketplace.infrastructure.dto.AddCartItemRequest;
import com.example.marketplace.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddItemCartService {

    private final FindProductService findProductService;
    private final FindCartService findCartService;
    private final CreateCartService createCartService;
    private final UpdateCartService updateCartService;

    @Transactional
    public Cart addProductToCart(UUID userId, List<AddCartItemRequest> requestList) {
        log.info("Adicionando {} item(ns) ao carrinho do usuário {}", requestList.size(), userId);

        var cart = findCartService
                .findCartByUserId(userId)
                .orElseGet(() -> {
                    log.info("Carrinho não encontrado, criando novo para usuário {}", userId);
                    return createCartService.createCart(userId);
                });

        cart.setUserId(userId);

        for (var request : requestList) {
            var product = findProductService.getProductById(request.productId());

            validateStock(product.getQuantidade(), request.quantity());

            cart.addItem(product, request.quantity());
            log.debug("Item adicionado: produto={}, qtd={}", product.getName(), request.quantity());
        }

        var saved = updateCartService.updateCart(cart);
        log.info("Carrinho atualizado com sucesso para usuário {}", userId);
        return saved;
    }

    private void validateStock(Integer stock, Integer quantity) {
        if (stock < quantity) {
            log.warn("Estoque insuficiente: disponível={}, solicitado={}", stock, quantity);
            throw new BusinessException("Estoque insuficiente para o produto");
        }
    }
}
