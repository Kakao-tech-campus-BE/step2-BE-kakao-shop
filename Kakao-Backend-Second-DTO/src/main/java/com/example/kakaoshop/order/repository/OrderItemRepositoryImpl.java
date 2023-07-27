package com.example.kakaoshop.order.repository;

import com.example.kakaoshop.order.domain.service.OrderItemRepository;
import com.example.kakaoshop.order.entity.OrderItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {
    private final OrderItemJpaRepository orderItemRepository;
    @Override
    public OrderItemEntity save(OrderItemEntity orderItemEntity) {
        return orderItemRepository.save(orderItemEntity);
    }
}
