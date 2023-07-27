package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {

    @Query("select o from Order o where o.id = :orderId")
    Optional<Order> findByOrderId(int orderId);

    @Query("select o from Order o  where o.id = :orderId and o.user.id = :userId")
    Optional<Cart> findByOrderIdAndUserId(@Param("orderId") int orderId, @Param("userId") int userId);

    @Query("select o from Order o  where  o.user.id = :userId")
    Order findByUserId(int userId);
}
