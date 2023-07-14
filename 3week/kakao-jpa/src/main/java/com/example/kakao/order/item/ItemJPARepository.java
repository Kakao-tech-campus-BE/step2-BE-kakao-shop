package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {
    Optional<Item> findByOrderId(int orderId);
    Optional<Item> findByOptionId(int optionId);
}
