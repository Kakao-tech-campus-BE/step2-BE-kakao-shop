package com.example.kakao.order.item;

import com.example.kakao.cart.Cart;
import com.example.kakao.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {
    @Query("select i from Item i where i.id = :orderId")
    List<Item> mFindByUserId(@Param("orderId") int orderId);
}
