package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {
    Optional<Item> findById(int id);
    List<Item> findByOrderId(@Param("orderId")int orderId);
}
