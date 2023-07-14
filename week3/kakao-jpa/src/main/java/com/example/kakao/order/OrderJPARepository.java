package com.example.kakao.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {

    @Query("select o from Order o " +
            "join fetch o.user u " +
            "where u.id = :userId")
    List<Order> findByUserId(int userId);
}
