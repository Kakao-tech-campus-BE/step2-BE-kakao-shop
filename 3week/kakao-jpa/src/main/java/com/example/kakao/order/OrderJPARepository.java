package com.example.kakao.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    Order save(Order order);

    Optional<Order> findById(int id);

    List<Order> findAllByUserId(int userId);
}
