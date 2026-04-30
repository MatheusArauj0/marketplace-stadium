package com.example.marketplace.infrastructure.controller;

import com.example.marketplace.application.AddItemCartService;
import com.example.marketplace.application.DeleteCartItemService;
import com.example.marketplace.application.FindCartService;
import com.example.marketplace.application.UpdateCartItemService;
import com.example.marketplace.infrastructure.dto.AddCartItemRequest;
import com.example.marketplace.infrastructure.dto.CartItemResponse;
import com.example.marketplace.infrastructure.dto.UpdateCartItemRequest;
import com.example.marketplace.infrastructure.mapper.CartMapper;
import com.example.marketplace.infrastructure.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Carrinho", description = "Gerenciamento do carrinho de compras do torcedor")
@RestController
@RequestMapping("api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final AddItemCartService addItemCartService;
    private final FindCartService findCartService;
    private final UpdateCartItemService updateCartItemService;
    private final DeleteCartItemService deleteCartItemService;
    private final CartMapper mapper;

    @Operation(summary = "Listar itens do carrinho",
            description = "Retorna os itens do carrinho do torcedor autenticado com paginação.")
    @GetMapping
    public ResponseEntity<Page<CartItemResponse>> findAllCarts(
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(findCartService.findCartByUserIdPaged(
                SecurityUtils.getAuthenticatedUserId(), pageable)
                .map(mapper::toCartItemResponse));
    }

    @Operation(summary = "Adicionar item ao carrinho",
            description = "Adiciona um ou mais produtos ao carrinho. Informe productId e quantity.")
    @PostMapping("/items")
    public ResponseEntity<CartItemResponse> addItemToCart(@RequestBody List<AddCartItemRequest> request) {
        return ResponseEntity.ok(mapper.toCartItemResponse(
                addItemCartService.addProductToCart(SecurityUtils.getAuthenticatedUserId(), request)));
    }

    @Operation(summary = "Atualizar quantidade do item",
            description = "Altera a quantidade de um item já existente no carrinho.")
    @PatchMapping("/items/{id}")
    public ResponseEntity<Void> updateItem(
            @Parameter(description = "ID do item no carrinho") @PathVariable("id") UUID idItem,
            @RequestBody UpdateCartItemRequest request) {
        updateCartItemService.updateCartItem(idItem, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remover item do carrinho",
            description = "Remove um item específico do carrinho.")
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> removeItem(
            @Parameter(description = "ID do item no carrinho") @PathVariable("id") UUID idItem) {
        deleteCartItemService.removeItem(idItem);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Limpar carrinho",
            description = "Remove todos os itens do carrinho.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAllItens(
            @Parameter(description = "ID do carrinho") @PathVariable("id") UUID cartId) {
        deleteCartItemService.removeAllItens(cartId);
        return ResponseEntity.noContent().build();
    }
}
