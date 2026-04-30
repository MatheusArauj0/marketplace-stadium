package com.example.marketplace.infrastructure.controller;

import com.example.marketplace.application.FindUserService;
import com.example.marketplace.infrastructure.dto.UserResponse;
import com.example.marketplace.infrastructure.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Usuários", description = "Consulta de usuários do sistema")
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final FindUserService findUserService;

    @Operation(summary = "Listar todos os usuários",
            description = "Retorna todos os usuários cadastrados. Requer papel ADMIN.")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(findUserService.findAll());
    }

    @Operation(summary = "Buscar usuário por ID",
            description = "Retorna os dados de um usuário. ADMIN pode ver qualquer um; usuário comum só vê a si mesmo.")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal")
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "ID do usuário") @PathVariable("id") UUID id) {
        return ResponseEntity.ok(findUserService.findById(id));
    }

    @Operation(summary = "Meu perfil",
            description = "Retorna os dados do usuário autenticado.")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        UUID userId = SecurityUtils.getAuthenticatedUserId();
        return ResponseEntity.ok(findUserService.findById(userId));
    }
}
