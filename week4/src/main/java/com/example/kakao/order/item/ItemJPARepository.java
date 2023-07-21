package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {
    @Query("SELECT i FROM Item i WHERE i.order.id = :orderId")
    List<Item> findItemsByOrderId(Integer orderId);
}
