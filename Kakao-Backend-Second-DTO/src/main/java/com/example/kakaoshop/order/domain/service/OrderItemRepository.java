package com.example.kakaoshop.order.domain.service;

import com.example.kakaoshop.order.entity.OrderItemEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository {
    OrderItemEntity save(OrderItemEntity orderItemEntity);
}
