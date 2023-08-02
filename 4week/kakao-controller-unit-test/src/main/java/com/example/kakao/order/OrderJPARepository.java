package com.example.kakao.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    Optional<Order> findById(int orderId);
}
