package com.example.kakao.order.item;

import com.example.kakao.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

    @Query("SELECT i FROM Item i JOIN fETCH i.option WHERE i.order.id = :orderId")
    public List<Item> findItemsWithOptionUsingFetchJoinById(@Param("orderId") Integer orderId);

    @Query("SELECT i FROM Item i WHERE i.order.id = :orderId")
    public List<Item> findItemsByOrderId(@Param("orderId") Integer orderId);

}
