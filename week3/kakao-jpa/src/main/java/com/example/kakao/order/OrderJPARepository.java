package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o join fetch o.user")
    Order mFindOrder();
}
