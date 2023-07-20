package com.example.kakao.order.item;

import com.example.kakao.cart.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.repository.query.Param;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

  List<Item> findByOrderId(@Param("orderId") int orderId);
}
