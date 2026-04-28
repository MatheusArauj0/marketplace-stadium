package com.example.marketplace.infrastructure.repository.jpa;

import com.example.marketplace.domain.models.Order;
import com.example.marketplace.infrastructure.entities.OrderEntity;
import com.example.marketplace.infrastructure.mapper.OrderMapper;
import com.example.marketplace.infrastructure.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryJpa {

    private final OrderRepository repository;
    private final OrderMapper orderMapper;

    public OrderEntity createOrder(Order order) {
        var entity = orderMapper.toOrderEntity(order);
        entity.getItems().forEach(item -> item.setOrder(entity));
        return repository.save(entity);
    }

    public List<OrderEntity> findByUserId(UUID userId) {
        return repository.findByUserId(userId);
    }
}
