package com.example.kakao.order;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    @EntityGraph("OrderWithUser")
    List<Order> findByUserId(int userId);

    @EntityGraph("OrderWithUser")
    Optional<Order> findById(int id);
}
