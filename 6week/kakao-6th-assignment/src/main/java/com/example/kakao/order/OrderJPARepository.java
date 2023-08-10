package com.example.kakao.order;

import com.example.kakao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {

    @Query("select o from Order o where o.id = :orderId")
    Order findById (int orderId);

    @Query("select o from Order o where o.user.id = :userId")
    List<Order> findAllUserId(int userId);


    @Query("select o from Order o join o.user where o.user.id = :userId and o.id = :orderItemId")
    Order findByUserIdAndOrderItemId(int userId, int orderItemId);
}
