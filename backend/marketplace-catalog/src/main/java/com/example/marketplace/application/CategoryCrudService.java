package com.example.marketplace.application;

import com.example.marketplace.infrastructure.dto.CategoryRequest;
import com.example.marketplace.infrastructure.dto.CategoryResponse;
import com.example.marketplace.infrastructure.entities.CategoryEntity;
import com.example.marketplace.infrastructure.mapper.CategoryMapper;
import com.example.marketplace.infrastructure.repository.CategoryRepository;
import com.example.marketplace.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryCrudService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Transactional(readOnly = true)
    public Page<CategoryResponse> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(mapper::toCategoryResponse);
    }

    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        log.info("Criando categoria: nome={}", request.name());
        CategoryEntity entity = new CategoryEntity();
        entity.setName(request.name());
        entity.setCreatedAt(Instant.now());
        CategoryEntity saved = categoryRepository.save(entity);
        log.info("Categoria criada com ID={}", saved.getId());
        return mapper.toCategoryResponse(saved);
    }

    @Transactional
    public CategoryResponse update(UUID id, CategoryRequest request) {
        log.info("Atualizando categoria ID={}", id);
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        entity.setName(request.name());
        entity.setUpdatedAt(Instant.now());
        return mapper.toCategoryResponse(categoryRepository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        log.info("Excluindo categoria ID={}", id);
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada");
        }
        categoryRepository.deleteById(id);
        log.info("Categoria ID={} excluída", id);
    }
}
