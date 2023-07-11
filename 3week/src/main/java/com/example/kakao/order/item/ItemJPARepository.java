package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

  @Query("select i " +
    "from Item i " +
    "join fetch i.order o " +
    "join fetch i.option op " +
    "where o.id = :id")
  List<Item> findAllByOrderId(int id);
}
