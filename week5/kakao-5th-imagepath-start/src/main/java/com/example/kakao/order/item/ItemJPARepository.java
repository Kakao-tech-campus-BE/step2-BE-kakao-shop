package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

    // 쿼리 수정 필요
    List<Item> findAllByOrderId(int orderId);
}