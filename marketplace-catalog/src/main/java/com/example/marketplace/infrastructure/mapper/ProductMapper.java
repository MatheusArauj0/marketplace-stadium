package com.example.marketplace.infrastructure.mapper;

import com.example.marketplace.domain.models.Product;
import com.example.marketplace.infrastructure.dto.ProductResponse;
import com.example.marketplace.infrastructure.entities.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductEntity entity);
    ProductEntity toProductEntity(Product product);
    ProductResponse toProductResponse(Product product);
}
