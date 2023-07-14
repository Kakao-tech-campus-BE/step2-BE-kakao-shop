package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {
    List<Item> findByOrderId(@Param("OrderId") int orderId);

    @Modifying
    @Query("select i from Item i join fetch i.order where i.order.id = :orderId")
    List<Item> mFindByOrderId(@Param("orderId")int orderId);

}
