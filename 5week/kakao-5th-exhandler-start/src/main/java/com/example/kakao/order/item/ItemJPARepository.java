package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

    @Query("SELECT i FROM Item i join fetch i.order o join fetch i.option op join fetch op.product p WHERE i.order.id = :orderId " +
            "order by i.option.product.id asc, i.order.id asc")
    List<Item> findByOrderId(@Param("orderId") int orderId);
}