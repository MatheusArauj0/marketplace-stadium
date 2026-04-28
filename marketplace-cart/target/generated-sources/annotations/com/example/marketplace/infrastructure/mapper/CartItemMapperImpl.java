package com.example.marketplace.infrastructure.mapper;

import com.example.marketplace.domain.model.CartItem;
import com.example.marketplace.infrastructure.entities.CartItemEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-16T00:27:14-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class CartItemMapperImpl implements CartItemMapper {

    @Override
    public CartItem toCartItem(CartItemEntity cartItemEntity) {
        if ( cartItemEntity == null ) {
            return null;
        }

        CartItem cartItem = new CartItem();

        cartItem.setId( cartItemEntity.getId() );
        cartItem.setProductId( cartItemEntity.getProductId() );
        cartItem.setQuantity( cartItemEntity.getQuantity() );
        cartItem.setPriceAtMoment( cartItemEntity.getPriceAtMoment() );

        return cartItem;
    }

    @Override
    public CartItemEntity toCartItemEntity(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }

        CartItemEntity cartItemEntity = new CartItemEntity();

        cartItemEntity.setId( cartItem.getId() );
        cartItemEntity.setProductId( cartItem.getProductId() );
        cartItemEntity.setQuantity( cartItem.getQuantity() );
        cartItemEntity.setPriceAtMoment( cartItem.getPriceAtMoment() );

        return cartItemEntity;
    }
}
