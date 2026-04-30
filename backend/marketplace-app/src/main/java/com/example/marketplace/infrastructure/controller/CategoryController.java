package com.example.marketplace.infrastructure.controller;

import com.example.marketplace.application.CategoryCrudService;
import com.example.marketplace.application.CategoryService;
import com.example.marketplace.infrastructure.dto.CategoryRequest;
import com.example.marketplace.infrastructure.dto.CategoryResponse;
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

@Tag(name = "Categorias", description = "Gerenciamento de categorias de produtos")
@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryCrudService categoryCrudService;

    @Operation(summary = "Listar categorias", description = "Retorna todas as categorias com paginação. Endpoint público.")
    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(categoryCrudService.findAll(pageable));
    }

    @Operation(summary = "Buscar categoria por ID", description = "Retorna os detalhes de uma categoria específica. Endpoint público.")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(
            @Parameter(description = "ID da categoria") @PathVariable("id") UUID id) {
        return ResponseEntity.ok(categoryService.getProductById(id));
    }

    @Operation(summary = "Criar categoria", description = "Cadastra uma nova categoria. Requer papel SELLER ou ADMIN.")
    @PostMapping
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryCrudService.create(request));
    }

    @Operation(summary = "Atualizar categoria", description = "Atualiza o nome de uma categoria existente. Requer papel SELLER ou ADMIN.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<CategoryResponse> updateCategory(
            @Parameter(description = "ID da categoria") @PathVariable("id") UUID id,
            @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryCrudService.update(id, request));
    }

    @Operation(summary = "Excluir categoria", description = "Remove uma categoria. Requer papel SELLER ou ADMIN.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID da categoria") @PathVariable("id") UUID id) {
        categoryCrudService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
