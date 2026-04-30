package com.example.marketplace.domain.model;

import com.example.marketplace.shared.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    private UUID id;
    private UUID userId;
    private BigDecimal balance;

    public void credit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Valor de recarga deve ser positivo");
        }
        this.balance = this.balance.add(amount);
    }

    public void debit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Valor de débito deve ser positivo");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new BusinessException(
                    "Saldo insuficiente. Disponível: R$ " + this.balance + ", necessário: R$ " + amount);
        }
        this.balance = this.balance.subtract(amount);
    }

    public boolean hasSufficientBalance(BigDecimal amount) {
        return this.balance.compareTo(amount) >= 0;
    }
}
