package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

    // 쿼리 수정 필요
    // @Query(value = "SELECT * FROM item WHERE order_id = ?1", nativeQuery = true)
    List<Item> findAllByOrderId(int orderId);
}