package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByUserId(@Param("userId") int userId);

    Cart findByUserIdAndOptionId(@Param("userId") int userId, @Param("optionId") int optionId);

    Cart findByUserIdAndOptionIdAndId(@Param("userId") int userId, @Param("optionId") int optionId, @Param("id") int id);}
