package com.example.marketplace.infrastructure.controller;

import com.example.marketplace.application.WalletService;
import com.example.marketplace.infrastructure.dto.CreditWalletRequest;
import com.example.marketplace.infrastructure.dto.TransactionResponse;
import com.example.marketplace.infrastructure.dto.WalletResponse;
import com.example.marketplace.infrastructure.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<WalletResponse> createWallet() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(walletService.createWallet(SecurityUtils.getAuthenticatedUserId()));
    }

    @GetMapping
    public ResponseEntity<WalletResponse> getMyWallet() {
        return ResponseEntity.ok(
                walletService.getWalletByUserId(SecurityUtils.getAuthenticatedUserId()));
    }

    @PostMapping("/credit")
    public ResponseEntity<WalletResponse> creditWallet(@Valid @RequestBody CreditWalletRequest request) {
        return ResponseEntity.ok(
                walletService.credit(
                        SecurityUtils.getAuthenticatedUserId(),
                        request.amount(),
                        "Recarga de saldo",
                        null));
    }

    @GetMapping("/transactions")
    public ResponseEntity<Page<TransactionResponse>> getTransactions(Pageable pageable) {
        return ResponseEntity.ok(
                walletService.getTransactions(SecurityUtils.getAuthenticatedUserId(), pageable));
    }
}
