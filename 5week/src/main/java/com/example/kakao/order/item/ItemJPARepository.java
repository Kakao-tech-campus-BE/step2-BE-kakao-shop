package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {
    // 주문 ID(orderId)에 해당하는 모든 아이템(Item)을 조회
    @Query("select i from Item i join fetch i.order join fetch i.option o join fetch o.product p where i.order.id = :orderId")
    List<Item> findAllByOrderId(@Param("orderId") int orderId);
}
