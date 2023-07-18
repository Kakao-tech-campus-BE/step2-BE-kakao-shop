package com.example.kakao.order;

import com.example.kakao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {

        Optional<Order> findOrdersByUser(User user);
}
