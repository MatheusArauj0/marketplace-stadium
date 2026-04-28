package com.example.marketplace.application;

import com.example.marketplace.infrastructure.repository.ProductRepository;
import com.example.marketplace.shared.exception.InsufficientStockException;
import com.example.marketplace.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    private final ProductRepository productRepository;

    @Transactional
    public void decrementStock(UUID productId, int quantity) {
        int updated = productRepository.decrementStock(productId, quantity);
        if (updated == 0) {
            var product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Produto", productId));
            throw new InsufficientStockException(productId, quantity, product.getQuantidade());
        }
        log.info("Estoque decrementado: productId={}, qty={}", productId, quantity);
    }

    @Transactional
    public void incrementStock(UUID productId, int quantity) {
        var product = productRepository.findByIdForUpdate(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", productId));
        product.setQuantidade(product.getQuantidade() + quantity);
        productRepository.save(product);
        log.info("Estoque incrementado: productId={}, qty={}", productId, quantity);
    }
}
