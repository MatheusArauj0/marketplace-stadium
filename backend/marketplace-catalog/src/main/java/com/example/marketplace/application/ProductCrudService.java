package com.example.marketplace.application;

import com.example.marketplace.infrastructure.dto.ProductRequest;
import com.example.marketplace.infrastructure.dto.ProductResponse;
import com.example.marketplace.infrastructure.entities.CategoryEntity;
import com.example.marketplace.infrastructure.entities.ProductEntity;
import com.example.marketplace.infrastructure.mapper.ProductMapper;
import com.example.marketplace.infrastructure.repository.CategoryRepository;
import com.example.marketplace.infrastructure.repository.ProductRepository;
import com.example.marketplace.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCrudService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(Pageable pageable) {
        log.debug("Listando produtos — página={}, tamanho={}", pageable.getPageNumber(), pageable.getPageSize());
        return productRepository.findAll(pageable)
                .map(mapper::toProductResponseFromEntity);
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        log.info("Criando produto: nome={}, preço={}", request.name(), request.price());
        ProductEntity entity = new ProductEntity();
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setPrice(request.price());
        entity.setImgUrl(request.imgUrl());
        entity.setQuantidade(request.quantidade());
        entity.setDate(Instant.now());
        entity.setCategories(resolveCategories(request.categoryIds()));

        ProductEntity saved = productRepository.save(entity);
        log.info("Produto criado com ID={}", saved.getId());
        return mapper.toProductResponseFromEntity(saved);
    }

    @Transactional
    public ProductResponse update(UUID id, ProductRequest request) {
        log.info("Atualizando produto ID={}", id);
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setPrice(request.price());
        entity.setImgUrl(request.imgUrl());
        entity.setQuantidade(request.quantidade());
        entity.setCategories(resolveCategories(request.categoryIds()));

        ProductEntity saved = productRepository.save(entity);
        log.info("Produto ID={} atualizado com sucesso", id);
        return mapper.toProductResponseFromEntity(saved);
    }

    @Transactional
    public void delete(UUID id) {
        log.info("Excluindo produto ID={}", id);
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado");
        }
        productRepository.deleteById(id);
        log.info("Produto ID={} excluído", id);
    }

    private Set<CategoryEntity> resolveCategories(Set<UUID> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(categoryRepository.findAllById(categoryIds));
    }
}
