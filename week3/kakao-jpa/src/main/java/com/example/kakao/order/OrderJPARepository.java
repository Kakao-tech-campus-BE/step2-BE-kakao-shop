package com.example.kakao.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(int userId);

    Order findById(int id);
}
