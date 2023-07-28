package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import org.springframework.data.repository.query.Param;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

  @Query("select i from Item i join fetch i.order where i.order.id = :orderId order By i.order.id asc")
  List<Item> findByOrderId(@Param("orderId") int orderId);
}
