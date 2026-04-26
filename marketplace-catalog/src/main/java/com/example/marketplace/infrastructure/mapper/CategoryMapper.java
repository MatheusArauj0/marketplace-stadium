package com.example.marketplace.infrastructure.mapper;

import com.example.marketplace.infrastructure.dto.CategoryResponse;
import com.example.marketplace.infrastructure.entities.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toCategoryResponse(CategoryEntity entity);
}
