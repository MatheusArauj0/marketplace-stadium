package com.example.marketplace.infrastructure.mapper;

import com.example.marketplace.domain.model.Cart;
import com.example.marketplace.infrastructure.dto.CartItemResponse;
import com.example.marketplace.infrastructure.entities.CartEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CartMapper {

    Cart toCart(CartEntity cartEntity);
    CartEntity toCartEntity(Cart cart);
    CartItemResponse toCartItemResponse(Cart cart);

    @AfterMapping
    default void linkCartItems(@MappingTarget CartEntity cartEntity) {

        if (cartEntity.getItems() != null) {
            cartEntity.getItems()
                    .forEach(item -> item.setCart(cartEntity));
        }

    }

}
