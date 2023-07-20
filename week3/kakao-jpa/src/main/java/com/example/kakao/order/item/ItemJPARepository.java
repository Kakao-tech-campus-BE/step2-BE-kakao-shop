package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


//order에 어떤 item이 포함되는지 확인할 수 있는 쿼리를 보내는 리포지토리.
public interface ItemJPARepository extends JpaRepository<Item, Integer> {
    @Query("select i from Item i join fetch i.order where i.order.id = :orderId")
    List<Item> findByOrderId(@Param("orderId") int orderId);
}
