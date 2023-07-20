package com.example.kakao.order.item;

import com.example.kakao.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

    @Query("select i from Item i where i.order.id = :orderId")
    List<Item> findByOrderId(@Param("orderId") int orderId);

    @Query("select i from Item i join fetch i.option op join fetch i.order o where i.order.id = :orderId")
    List<Item> findByOrderIdJoinFetch(@Param("orderId") int orderId);

    @Query("select i from Item i join fetch i.option op join fetch i.order o join fetch op.product join fetch o.user where i.order.id = :orderId")
    List<Item> findByOrderIdJoinFetchAll(@Param("orderId") int orderId);
}
