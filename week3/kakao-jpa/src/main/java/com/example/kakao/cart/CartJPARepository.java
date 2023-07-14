package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query
    List<Cart> findById(int id);

    @Query
    Cart updateQuantity(int id, int quantity);
}