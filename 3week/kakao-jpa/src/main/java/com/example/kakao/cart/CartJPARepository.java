package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    Optional<List<Cart>> findByUserId(@Param("userId")int userId);
    Optional<Cart> findByOptionId(@Param("optionId")int optionId);
    Optional<Cart> deleteByUserId(int userId);
    @Query("SELECT c FROM Cart c JOIN fetch c.user u JOIN fetch c.option o JOIN FETCH o.product WHERE u.id = :userId AND o.id = :optionId")
    Optional<Cart> findByUserIdAndOptionId(@Param("userId") int userId, @Param("optionId") int optionId);
}
