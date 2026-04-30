package com.example.marketplace.infrastructure.controller;

import com.example.marketplace.application.WalletService;
import com.example.marketplace.infrastructure.dto.CreditWalletRequest;
import com.example.marketplace.infrastructure.dto.TransactionResponse;
import com.example.marketplace.infrastructure.dto.WalletResponse;
import com.example.marketplace.infrastructure.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Carteira", description = "Carteira digital do torcedor — saldo, recarga e extrato")
@RestController
@RequestMapping("api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @Operation(summary = "Criar carteira",
            description = "Cria uma carteira digital para o torcedor autenticado. Cada usuário pode ter apenas uma.")
    @PostMapping
    public ResponseEntity<WalletResponse> createWallet() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(walletService.createWallet(SecurityUtils.getAuthenticatedUserId()));
    }

    @Operation(summary = "Consultar saldo",
            description = "Retorna o saldo atual da carteira do torcedor autenticado.")
    @GetMapping
    public ResponseEntity<WalletResponse> getMyWallet() {
        return ResponseEntity.ok(
                walletService.getWalletByUserId(SecurityUtils.getAuthenticatedUserId()));
    }

    @Operation(summary = "Recarregar carteira",
            description = "Adiciona créditos à carteira do torcedor. Informe o valor da recarga.")
    @PostMapping("/credit")
    public ResponseEntity<WalletResponse> creditWallet(@Valid @RequestBody CreditWalletRequest request) {
        return ResponseEntity.ok(
                walletService.credit(
                        SecurityUtils.getAuthenticatedUserId(),
                        request.amount(),
                        "Recarga de saldo",
                        null));
    }

    @Operation(summary = "Extrato de transações",
            description = "Lista todas as transações (débitos e créditos) da carteira com paginação.")
    @GetMapping("/transactions")
    public ResponseEntity<Page<TransactionResponse>> getTransactions(
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(
                walletService.getTransactions(SecurityUtils.getAuthenticatedUserId(), pageable));
    }
}
