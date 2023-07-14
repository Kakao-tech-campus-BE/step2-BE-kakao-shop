package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {
    @EntityGraph(attributePaths = {"option", "order", "option.product", "order.user"})
    @Query("SELECT i FROM Item i WHERE i.order.id = :orderId")
    List<Item> findByOrderId(@Param("orderId") int orderId);

    @EntityGraph(attributePaths = {"option", "order", "option.product", "order.user"})
    @Query("SELECT i FROM Item i WHERE i.id = :itemId")
    Optional<Item> findByItemId(@Param("itemId") int itemId);
}
