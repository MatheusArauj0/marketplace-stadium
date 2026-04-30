package com.example.marketplace.infrastructure.controller;

import com.example.marketplace.application.FindProductService;
import com.example.marketplace.application.ProductCrudService;
import com.example.marketplace.infrastructure.dto.ProductRequest;
import com.example.marketplace.infrastructure.dto.ProductResponse;
import com.example.marketplace.infrastructure.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Produtos", description = "Gerenciamento do catálogo de produtos do estádio")
@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final FindProductService findProductService;
    private final ProductCrudService productCrudService;
    private final ProductMapper mapper;

    @Operation(summary = "Listar produtos", description = "Retorna todos os produtos do catálogo com paginação. Endpoint público.")
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(productCrudService.findAll(pageable));
    }

    @Operation(summary = "Buscar produto por ID", description = "Retorna os detalhes de um produto específico. Endpoint público.")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
            @Parameter(description = "ID do produto") @PathVariable("id") UUID id) {
        return ResponseEntity.ok(mapper.toProductResponse(findProductService.getProductById(id)));
    }

    @Operation(summary = "Criar produto", description = "Cadastra um novo produto no catálogo. Requer papel SELLER ou ADMIN.")
    @PostMapping
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productCrudService.create(request));
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente. Requer papel SELLER ou ADMIN.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "ID do produto") @PathVariable("id") UUID id,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productCrudService.update(id, request));
    }

    @Operation(summary = "Excluir produto", description = "Remove um produto do catálogo. Requer papel SELLER ou ADMIN.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID do produto") @PathVariable("id") UUID id) {
        productCrudService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
