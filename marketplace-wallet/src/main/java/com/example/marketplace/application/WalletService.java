package com.example.marketplace.application;

import com.example.marketplace.domain.enums.TransactionType;
import com.example.marketplace.infrastructure.dto.TransactionResponse;
import com.example.marketplace.infrastructure.dto.WalletResponse;
import com.example.marketplace.infrastructure.entities.WalletEntity;
import com.example.marketplace.infrastructure.entities.WalletTransactionEntity;
import com.example.marketplace.infrastructure.mapper.WalletMapper;
import com.example.marketplace.infrastructure.repository.WalletRepository;
import com.example.marketplace.infrastructure.repository.WalletTransactionRepository;
import com.example.marketplace.shared.exception.BusinessException;
import com.example.marketplace.shared.exception.DuplicateResourceException;
import com.example.marketplace.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionRepository transactionRepository;
    private final WalletMapper mapper;

    @Transactional
    public WalletResponse createWallet(UUID userId) {
        if (walletRepository.existsByUserId(userId)) {
            throw new DuplicateResourceException("Wallet já existe para este usuário");
        }

        var wallet = new WalletEntity();
        wallet.setUserId(userId);
        wallet.setBalance(BigDecimal.ZERO);

        var saved = walletRepository.save(wallet);
        log.info("Wallet criada para userId={}", userId);
        return mapper.toWalletResponse(saved);
    }

    @Transactional(readOnly = true)
    public WalletResponse getWalletByUserId(UUID userId) {
        var wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet", userId));
        return mapper.toWalletResponse(wallet);
    }

    @Transactional
    public WalletResponse credit(UUID userId, BigDecimal amount, String description, UUID referenceId) {
        var wallet = walletRepository.findByUserIdForUpdate(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet", userId));

        wallet.setBalance(wallet.getBalance().add(amount));
        wallet.setUpdatedAt(java.time.Instant.now());

        var transaction = new WalletTransactionEntity();
        transaction.setWallet(wallet);
        transaction.setType(TransactionType.CREDIT);
        transaction.setAmount(amount);
        transaction.setBalanceAfter(wallet.getBalance());
        transaction.setDescription(description);
        transaction.setReferenceId(referenceId);

        walletRepository.save(wallet);
        transactionRepository.save(transaction);

        log.info("Crédito de R${} na wallet userId={} | saldo atual: R${}",
                amount, userId, wallet.getBalance());

        return mapper.toWalletResponse(wallet);
    }

    @Transactional
    public WalletResponse debit(UUID userId, BigDecimal amount, String description, UUID referenceId) {
        var wallet = walletRepository.findByUserIdForUpdate(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet", userId));

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new BusinessException(
                    "Saldo insuficiente. Disponível: R$ " + wallet.getBalance()
                            + ", necessário: R$ " + amount);
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        wallet.setUpdatedAt(java.time.Instant.now());

        var transaction = new WalletTransactionEntity();
        transaction.setWallet(wallet);
        transaction.setType(TransactionType.DEBIT);
        transaction.setAmount(amount);
        transaction.setBalanceAfter(wallet.getBalance());
        transaction.setDescription(description);
        transaction.setReferenceId(referenceId);

        walletRepository.save(wallet);
        transactionRepository.save(transaction);

        log.info("Débito de R${} na wallet userId={} | saldo atual: R${}",
                amount, userId, wallet.getBalance());

        return mapper.toWalletResponse(wallet);
    }

    @Transactional
    public WalletResponse refund(UUID userId, BigDecimal amount, String description, UUID referenceId) {
        return credit(userId, amount, "ESTORNO: " + description, referenceId);
    }

    @Transactional(readOnly = true)
    public Page<TransactionResponse> getTransactions(UUID userId, Pageable pageable) {
        var wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet", userId));

        return transactionRepository
                .findByWalletIdOrderByCreatedAtDesc(wallet.getId(), pageable)
                .map(mapper::toTransactionResponse);
    }
}
