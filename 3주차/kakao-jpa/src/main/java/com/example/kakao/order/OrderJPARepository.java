package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import com.example.kakao.user.User;
import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {

    Optional<Order> findById(int id);

    @Query("select o from Order o where o.id =:orderId")
    Optional<User> findByUser(@Param("user") User user);


}
