package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUserId(@Param("userId") int userId);
    void deleteAllByUserId(@Param("userId") int userId);
}
