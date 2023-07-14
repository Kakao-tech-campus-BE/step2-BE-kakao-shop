package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;
public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o join fetch o.user where o.id = :orderId")
    Optional<Order> findById(@Param("orderId")int orderId);

    @Query("select o from Order o join fetch o.user")
    List<Order> orderFindAll();
}
