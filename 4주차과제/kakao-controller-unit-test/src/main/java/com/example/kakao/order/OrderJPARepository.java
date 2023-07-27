package com.example.kakao.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {

    // 결제하기1 - 유저아이디로 주문아이디를 찾는다.
    Optional<Order> findByUserId(int userId);
}
