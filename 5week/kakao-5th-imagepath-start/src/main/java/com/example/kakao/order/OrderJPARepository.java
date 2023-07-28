package com.example.kakao.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    //userid로 order 정보 가져오기
    @Query("select o from Order o join fetch User u where o.user.id = :userId")
    List<Order> findByUserIdJoinOrder(@Param("userId") int userId);

    //order id로 조회
    @Query("select o from Order o where o.id = :orderId")
    Optional<Order> findOrderById(@Param("orderId") int orderId);

}
