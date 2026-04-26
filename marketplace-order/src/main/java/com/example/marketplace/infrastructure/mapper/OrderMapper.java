package com.example.marketplace.infrastructure.mapper;

import com.example.marketplace.domain.models.Order;
import com.example.marketplace.infrastructure.entities.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderEntity toOrderEntity(Order order);
    Order toOrder(OrderEntity orderEntity);
}
