package com.example.marketplace.infrastructure.repoitory.jpa;

import com.example.marketplace.domain.models.Order;
import com.example.marketplace.infrastructure.entities.OrderEntity;
import com.example.marketplace.infrastructure.mapper.OrderMapper;
import com.example.marketplace.infrastructure.repoitory.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderRepositoryJpa {
    private final OrderRepository repository;
    private final OrderMapper orderMapper;


    public OrderEntity createOrder(Order order) {
        return repository.save(orderMapper.toOrderEntity(order));
    }
}
