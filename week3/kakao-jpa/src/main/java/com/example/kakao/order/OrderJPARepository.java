package com.example.kakao.order;

import com.example.kakao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o join fetch o.user where o.user.id = :userId")
    List<Order> findByUserId(@Param("userId")int userId);

    @Query("select o from Order o join fetch o.user where o.id = :id")
    List<Order> findByOrderId(@Param("id")int id);
}
