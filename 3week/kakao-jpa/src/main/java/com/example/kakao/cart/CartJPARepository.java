package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT c FROM Cart c JOIN FETCH c.option o WHERE c.user.id = :userId")
    List<Cart> findByUserId(@Param("userId") int userId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.option WHERE c.user.id = :userId AND c.option.id = :optionId")
    Cart findByUserIdAndOptionId(@Param("userId") int userId, @Param("optionId") int optionId);
}