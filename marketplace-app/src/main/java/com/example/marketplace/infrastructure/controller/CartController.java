package com.example.marketplace.infrastructure.controller;

import com.example.marketplace.application.AddItemCartService;
import com.example.marketplace.application.DeleteCartItemService;
import com.example.marketplace.application.FindCartService;
import com.example.marketplace.application.UpdateCartItemService;
import com.example.marketplace.infrastructure.dto.AddCartItemRequest;
import com.example.marketplace.infrastructure.dto.CartItemResponse;
import com.example.marketplace.infrastructure.dto.UpdateCartItemRequest;
import com.example.marketplace.infrastructure.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

@RestController
@RequestMapping("api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final AddItemCartService addItemCartService;
    private final FindCartService findCartService;
    private final UpdateCartItemService updateCartItemService;
    private final DeleteCartItemService deleteCartItemService;
    private final CartMapper mapper;

    @GetMapping
    public ResponseEntity<Page<CartItemResponse>> findAllCarts(Pageable pageable) {
        return ResponseEntity.ok(findCartService.findAllCarts(pageable)
                .map(mapper::toCartItemResponse));
    }

    @PostMapping("/items")
    public ResponseEntity<CartItemResponse> addItemToCart(@RequestBody List<AddCartItemRequest> request) {
        return ResponseEntity.ok(mapper.toCartItemResponse(addItemCartService.addProductToCart(request)));
    }

    @PatchMapping("/items/{id}")
    public ResponseEntity<Void> updateItem(@PathVariable("id") UUID idItem, @RequestBody UpdateCartItemRequest request) {
        updateCartItemService.updateCartItem(idItem, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> removeItem(@PathVariable("id") UUID idItem) {
        deleteCartItemService.removeItem(idItem);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAllItens(@PathVariable("id") UUID cartId) {
        deleteCartItemService.removeAllItens(cartId);
        return ResponseEntity.noContent().build();
    }
}
