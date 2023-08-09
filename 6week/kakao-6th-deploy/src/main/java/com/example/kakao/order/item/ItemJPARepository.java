package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {
    List<Item> findAllByOrderId(int id);
}
