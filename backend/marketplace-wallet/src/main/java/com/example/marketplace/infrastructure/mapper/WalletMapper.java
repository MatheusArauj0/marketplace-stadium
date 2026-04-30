package com.example.marketplace.infrastructure.mapper;

import com.example.marketplace.infrastructure.dto.TransactionResponse;
import com.example.marketplace.infrastructure.dto.WalletResponse;
import com.example.marketplace.infrastructure.entities.WalletEntity;
import com.example.marketplace.infrastructure.entities.WalletTransactionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    WalletResponse toWalletResponse(WalletEntity entity);

    TransactionResponse toTransactionResponse(WalletTransactionEntity entity);
}
