package com.example.kakao.order.domain.service;

import com.example.kakao.order.entity.OrderEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository {
    OrderEntity save(OrderEntity orderEntity);

    Optional<OrderEntity> findById(Long orderId);
}
