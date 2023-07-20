package com.example.kakao.order.item;

import com.example.kakao.cart.Cart;
import com.example.kakao.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemJPARepository extends JpaRepository<Item, Integer> {

    @Query("select i " +
            "from Item i " +
            "join fetch i.option o " +
            "join fetch o.product " +
            "where i.order.id = :orderId ")
    List<Item> mFindByOrderId(@Param("orderId") int orderId);

}