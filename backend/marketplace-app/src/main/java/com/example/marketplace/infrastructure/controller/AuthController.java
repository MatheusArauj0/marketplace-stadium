package com.example.marketplace.infrastructure.controller;

import com.example.marketplace.infrastructure.security.AuthService;
import com.example.marketplace.infrastructure.security.dto.LoginRequest;
import com.example.marketplace.infrastructure.security.dto.RefreshTokenRequest;
import com.example.marketplace.infrastructure.security.dto.RegisterRequest;
import com.example.marketplace.infrastructure.security.dto.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticação", description = "Registro de torcedores/lojistas, login e refresh de token JWT")
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Registrar torcedor",
            description = "Cria uma conta de torcedor (ROLE_CUSTOMER). Exige CPF válido no campo 'document'.")
    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @Operation(summary = "Registrar lojista",
            description = "Cria uma conta de lojista (ROLE_SELLER). Exige CNPJ válido no campo 'document'.")
    @PostMapping("/register/seller")
    public ResponseEntity<TokenResponse> registerSeller(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerSeller(request));
    }

    @Operation(summary = "Login",
            description = "Autentica o usuário e retorna access token + refresh token JWT.")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Refresh token",
            description = "Gera um novo access token a partir de um refresh token válido.")
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refresh(request));
    }
}
