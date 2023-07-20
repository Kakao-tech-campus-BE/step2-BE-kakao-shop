package com.example.kakao.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {

    @Query("select o from Order o join fetch o.user where o.id = :id")
    Order findById(int id);
}
