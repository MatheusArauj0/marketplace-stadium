package com.example.marketplace.infrastructure.mapper;

import com.example.marketplace.domain.model.Cart;
import com.example.marketplace.domain.model.CartItem;
import com.example.marketplace.infrastructure.dto.CartItemResponse;
import com.example.marketplace.infrastructure.entities.CartEntity;
import com.example.marketplace.infrastructure.entities.CartItemEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-16T00:27:14-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Override
    public Cart toCart(CartEntity cartEntity) {
        if ( cartEntity == null ) {
            return null;
        }

        Cart cart = new Cart();

        cart.setId( cartEntity.getId() );
        cart.setUserId( cartEntity.getUserId() );
        cart.setItems( cartItemEntityListToCartItemList( cartEntity.getItems() ) );

        return cart;
    }

    @Override
    public CartEntity toCartEntity(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartEntity cartEntity = new CartEntity();

        cartEntity.setId( cart.getId() );
        cartEntity.setUserId( cart.getUserId() );
        cartEntity.setItems( cartItemListToCartItemEntityList( cart.getItems() ) );

        linkCartItems( cartEntity );

        return cartEntity;
    }

    @Override
    public CartItemResponse toCartItemResponse(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        UUID id = null;
        UUID userId = null;
        List<CartItem> items = null;

        id = cart.getId();
        userId = cart.getUserId();
        List<CartItem> list = cart.getItems();
        if ( list != null ) {
            items = new ArrayList<CartItem>( list );
        }

        CartItemResponse cartItemResponse = new CartItemResponse( id, userId, items );

        return cartItemResponse;
    }

    protected CartItem cartItemEntityToCartItem(CartItemEntity cartItemEntity) {
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

    protected List<CartItem> cartItemEntityListToCartItemList(List<CartItemEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<CartItem> list1 = new ArrayList<CartItem>( list.size() );
        for ( CartItemEntity cartItemEntity : list ) {
            list1.add( cartItemEntityToCartItem( cartItemEntity ) );
        }

        return list1;
    }

    protected CartItemEntity cartItemToCartItemEntity(CartItem cartItem) {
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

    protected List<CartItemEntity> cartItemListToCartItemEntityList(List<CartItem> list) {
        if ( list == null ) {
            return null;
        }

        List<CartItemEntity> list1 = new ArrayList<CartItemEntity>( list.size() );
        for ( CartItem cartItem : list ) {
            list1.add( cartItemToCartItemEntity( cartItem ) );
        }

        return list1;
    }
}
