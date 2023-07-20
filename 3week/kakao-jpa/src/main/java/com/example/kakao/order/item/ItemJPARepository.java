package com.example.kakao.order.item;

import com.example.kakao.product.option.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {
    @Query("SELECT i FROM Item i JOIN FETCH i.order JOIN FETCH i.option WHERE i.order.id = :orderId")
    List<Item> findByOrderId(@Param("orderId") int orderId);
}
