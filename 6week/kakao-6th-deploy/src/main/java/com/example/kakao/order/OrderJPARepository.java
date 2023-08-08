package com.example.kakao.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {

    boolean existsByIdAndUserId (Integer orderId, Integer userId);

}
