package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByUserId(int id);

    @Modifying
    @Query("UPDATE Cart c SET c.quantity = :quantity WHERE c.id = :id")
    void updateQuantityById(@Param("id") int id, @Param("quantity") int quantity);
}
