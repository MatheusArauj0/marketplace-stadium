package com.example.marketplace.infrastructure.mapper;

import com.example.marketplace.domain.models.Order;
import com.example.marketplace.domain.models.OrderItem;
import com.example.marketplace.infrastructure.entities.OrderEntity;
import com.example.marketplace.infrastructure.entities.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "items", source = "items")
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "pickupCode", source = "pickupCode")
    OrderEntity toOrderEntity(Order order);

    @Mapping(target = "order", ignore = true)
    OrderItemEntity toOrderItemEntity(OrderItem item);

    Order toOrder(OrderEntity orderEntity);

    OrderItem toOrderItem(OrderItemEntity entity);
}
