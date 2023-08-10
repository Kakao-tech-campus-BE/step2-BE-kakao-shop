package com.example.kakao.order.repository;

import com.example.kakao.order.domain.service.OrderRepository;
import com.example.kakao.order.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderJpaRepository orderRepository;
    @Override
    public OrderEntity save(OrderEntity orderEntity) {
        return orderRepository.save(orderEntity);
    }

    @Override
    public Optional<OrderEntity> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }
}
