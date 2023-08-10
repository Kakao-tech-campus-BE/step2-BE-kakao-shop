package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderJPARepository extends JpaRepository<Order, Integer> {


}
