package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserId(@Param("userId") int userId);

    Optional<Cart> findById(int id);

    List<Cart> deleteByUserId(@Param("userId") int userId);
}
