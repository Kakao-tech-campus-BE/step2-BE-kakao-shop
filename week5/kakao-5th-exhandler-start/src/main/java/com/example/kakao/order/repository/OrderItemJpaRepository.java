package com.example.kakao.order.repository;

import com.example.kakao.order.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, Long> {
}
