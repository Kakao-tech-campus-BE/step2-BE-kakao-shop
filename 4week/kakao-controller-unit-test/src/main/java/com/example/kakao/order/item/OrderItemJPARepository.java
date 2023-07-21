package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderItemJPARepository extends JpaRepository<OrderItem, Integer> {
    // 주문 id에 해당하는 주문 결과를 한 번의 쿼리로 조회
    List<OrderItem> findByOrderId(int orderId);
}
