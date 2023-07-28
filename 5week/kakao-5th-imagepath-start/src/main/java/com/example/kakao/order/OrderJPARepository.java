package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o  where o.user.id = :userId and o.id = :orderId")
    Optional<Order> findByUserIdAndOrderId(@Param("userId") int userId,@Param("orderId") int orderId);


}

