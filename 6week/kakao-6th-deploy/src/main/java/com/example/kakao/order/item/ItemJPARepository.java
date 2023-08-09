package com.example.kakao.order.item;

import com.example.kakao.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {
    @Query("select i from Item i where i.order.id = :orderId")
    List<Item> findAllByOrderId(@Param("orderId") int orderId);


    @Query("select i from Item i join fetch  i.option o join fetch  o.product p where i.order.id = :orderId")
    List<Item> findAllByOrderIdJoinOptionJoinProduct(@Param("orderId") int orderId);
}
