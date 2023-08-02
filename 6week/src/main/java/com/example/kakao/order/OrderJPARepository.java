package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {

}
