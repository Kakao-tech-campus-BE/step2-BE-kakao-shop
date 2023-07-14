package com.example.kakao.order.item;

import com.example.kakao.order.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {


    // 해당 방식을 JoinFetch 쿼리로 짜는것이 어렵습니다.
    @EntityGraph(attributePaths = {"option", "option.product"})
    @Query("SELECT i FROM Item i WHERE i.order.id = :orderId")
    List<Item> findByOrderId(@Param("orderId") int orderId);
}
