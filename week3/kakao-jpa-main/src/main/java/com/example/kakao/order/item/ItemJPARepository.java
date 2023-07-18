package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

    @Query("select i from Item i join fetch i.order where i.order.id = :orderId")
    List<Item> findAllByOrderId(@Param("orderId") int id);

    @Query("select i from Item i join fetch i.order join fetch i.option where i.order.id = :orderId")
    List<Item> findAllByOrderId2(@Param("orderId") int id);

    @Query("select i from Item i join fetch i.order left join fetch i.option where i.order.id = :orderId")
    List<Item> findAllByOrderId3(@Param("orderId") int id);
}
