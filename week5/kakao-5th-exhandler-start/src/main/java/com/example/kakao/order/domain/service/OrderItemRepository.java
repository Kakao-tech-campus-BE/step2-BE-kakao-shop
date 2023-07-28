package com.example.kakao.order.domain.service;

import com.example.kakao.order.entity.OrderItemEntity;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderItemRepository {
    OrderItemEntity save(OrderItemEntity orderItemEntity);
}
