package com.example.marketplace.infrastructure.mapper;

import com.example.marketplace.domain.model.CartItem;
import com.example.marketplace.infrastructure.entities.CartItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItem toCartItem(CartItemEntity cartItemEntity);
    CartItemEntity toCartItemEntity(CartItem cartItem);
}
