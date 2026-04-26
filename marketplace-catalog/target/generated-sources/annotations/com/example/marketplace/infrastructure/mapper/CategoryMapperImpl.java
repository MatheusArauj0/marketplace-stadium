package com.example.marketplace.infrastructure.mapper;

import com.example.marketplace.infrastructure.dto.CategoryResponse;
import com.example.marketplace.infrastructure.entities.CategoryEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-16T00:27:12-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryResponse toCategoryResponse(CategoryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryResponse.CategoryResponseBuilder categoryResponse = CategoryResponse.builder();

        categoryResponse.id( entity.getId() );
        categoryResponse.name( entity.getName() );
        categoryResponse.createdAt( entity.getCreatedAt() );

        return categoryResponse.build();
    }
}
