package com.example.kakao.order;

import com.example.kakao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    // 주어진 사용자 ID에 대해 한 번의 쿼리를 실행하여 주문 목록을 반환
    List<Order> findByUser(User user);
}
