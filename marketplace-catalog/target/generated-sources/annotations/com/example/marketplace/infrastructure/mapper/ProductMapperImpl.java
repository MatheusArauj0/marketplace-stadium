package com.example.marketplace.infrastructure.mapper;

import com.example.marketplace.domain.models.Category;
import com.example.marketplace.domain.models.Product;
import com.example.marketplace.infrastructure.dto.CategoryResponse;
import com.example.marketplace.infrastructure.dto.ProductResponse;
import com.example.marketplace.infrastructure.entities.CategoryEntity;
import com.example.marketplace.infrastructure.entities.ProductEntity;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-16T00:27:12-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toProduct(ProductEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( entity.getId() );
        product.setName( entity.getName() );
        product.setDescription( entity.getDescription() );
        product.setPrice( entity.getPrice() );
        product.setImgUrl( entity.getImgUrl() );
        product.setQrCode( entity.getQrCode() );
        if ( entity.getQuantidade() != null ) {
            product.setQuantidade( entity.getQuantidade().intValue() );
        }
        product.setDate( entity.getDate() );
        product.setCategories( categoryEntitySetToCategorySet( entity.getCategories() ) );

        return product;
    }

    @Override
    public ProductEntity toProductEntity(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setId( product.getId() );
        productEntity.setName( product.getName() );
        productEntity.setDescription( product.getDescription() );
        productEntity.setPrice( product.getPrice() );
        productEntity.setImgUrl( product.getImgUrl() );
        productEntity.setQrCode( product.getQrCode() );
        if ( product.getQuantidade() != null ) {
            productEntity.setQuantidade( product.getQuantidade().longValue() );
        }
        productEntity.setDate( product.getDate() );
        productEntity.setCategories( categorySetToCategoryEntitySet( product.getCategories() ) );

        return productEntity;
    }

    @Override
    public ProductResponse toProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.id( product.getId() );
        productResponse.name( product.getName() );
        productResponse.description( product.getDescription() );
        productResponse.price( product.getPrice() );
        productResponse.imgUrl( product.getImgUrl() );
        productResponse.qrCode( product.getQrCode() );
        if ( product.getQuantidade() != null ) {
            productResponse.quantidade( product.getQuantidade().longValue() );
        }
        productResponse.date( product.getDate() );
        productResponse.categories( categorySetToCategoryResponseSet( product.getCategories() ) );

        return productResponse.build();
    }

    protected Category categoryEntityToCategory(CategoryEntity categoryEntity) {
        if ( categoryEntity == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( categoryEntity.getId() );
        category.setName( categoryEntity.getName() );
        category.setCreatedAt( categoryEntity.getCreatedAt() );
        category.setUpdatedAt( categoryEntity.getUpdatedAt() );

        return category;
    }

    protected Set<Category> categoryEntitySetToCategorySet(Set<CategoryEntity> set) {
        if ( set == null ) {
            return null;
        }

        Set<Category> set1 = new LinkedHashSet<Category>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( CategoryEntity categoryEntity : set ) {
            set1.add( categoryEntityToCategory( categoryEntity ) );
        }

        return set1;
    }

    protected CategoryEntity categoryToCategoryEntity(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setId( category.getId() );
        categoryEntity.setName( category.getName() );
        categoryEntity.setCreatedAt( category.getCreatedAt() );
        categoryEntity.setUpdatedAt( category.getUpdatedAt() );

        return categoryEntity;
    }

    protected Set<CategoryEntity> categorySetToCategoryEntitySet(Set<Category> set) {
        if ( set == null ) {
            return null;
        }

        Set<CategoryEntity> set1 = new LinkedHashSet<CategoryEntity>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Category category : set ) {
            set1.add( categoryToCategoryEntity( category ) );
        }

        return set1;
    }

    protected CategoryResponse categoryToCategoryResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse.CategoryResponseBuilder categoryResponse = CategoryResponse.builder();

        categoryResponse.id( category.getId() );
        categoryResponse.name( category.getName() );
        categoryResponse.createdAt( category.getCreatedAt() );

        return categoryResponse.build();
    }

    protected Set<CategoryResponse> categorySetToCategoryResponseSet(Set<Category> set) {
        if ( set == null ) {
            return null;
        }

        Set<CategoryResponse> set1 = new LinkedHashSet<CategoryResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Category category : set ) {
            set1.add( categoryToCategoryResponse( category ) );
        }

        return set1;
    }
}
