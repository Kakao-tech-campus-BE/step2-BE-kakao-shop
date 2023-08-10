package com.example.kakao.order.domain.service;

import com.example.kakao.order.entity.OrderEntity;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository {
    OrderEntity save(OrderEntity orderEntity);

}
