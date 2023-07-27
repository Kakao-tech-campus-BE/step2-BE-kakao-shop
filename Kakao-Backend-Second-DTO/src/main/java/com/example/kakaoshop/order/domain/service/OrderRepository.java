package com.example.kakaoshop.order.domain.service;

import com.example.kakaoshop.order.entity.OrderEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {
    OrderEntity save(OrderEntity orderEntity);

}
