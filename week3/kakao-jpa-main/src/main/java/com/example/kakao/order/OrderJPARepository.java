package com.example.kakao.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o JOIN fetch o.user WHERE o.user.id = :userId")
    List<Order> findByUserId(@Param("userId") int userId);
    @Query("SELECT o FROM Order o JOIN fetch o.user WHERE o.id = :orderId")
    Optional<Order> findByOrderId(@Param("orderId") int orderId);
}
