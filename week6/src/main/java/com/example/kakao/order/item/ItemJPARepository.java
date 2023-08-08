package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

  @Query("select i from Item i where i.order.id = ?1")
  List<Item> findByOrderId(int id);
}