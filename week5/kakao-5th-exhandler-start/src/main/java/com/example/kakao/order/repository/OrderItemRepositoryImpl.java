package com.example.kakao.order.repository;

import com.example.kakao.order.domain.service.OrderItemRepository;
import com.example.kakao.order.entity.OrderItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {
    private final OrderItemJpaRepository orderItemRepository;
    @Override
    public OrderItemEntity save(OrderItemEntity orderItemEntity) {
        return orderItemRepository.save(orderItemEntity);
    }

    @Override
    public List<OrderItemEntity> saveAllAndFlush(List<OrderItemEntity> orderItemEntities) {
        return orderItemRepository.saveAllAndFlush(orderItemEntities);
    }
}
