package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c join fetch c.user where c.user.id = :userId")
    List<Cart> findCartByUserId(@Param("userId") int userId);
}
