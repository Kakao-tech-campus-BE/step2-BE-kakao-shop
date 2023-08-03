package com.example.kakao.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    Optional<Order> findById(int id);
    @Query("select o from Order o join fetch o.user where o.user.id = :userId")
    List<Order> findByUserId(@Param("userId") int userId);

    //userId, orderId를 대조하기 위한 검증용 쿼리!
    @Query("select o from Order o join o.user where o.user.id = :userId and o.id = :orderId")
    Optional<Order> findByUserIdAndOrderId(@Param("userId") int userId, @Param("orderId") int orderId);
}
