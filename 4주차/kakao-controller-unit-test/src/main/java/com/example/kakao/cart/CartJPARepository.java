package com.example.kakao.cart;

import com.example.kakao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT c FROM Cart c JOIN fetch c.option o JOIN fetch c.user u JOIN fetch o.product p WHERE c.user.id = :userId")
    List<Cart> findByUserId(int userId);

    Cart findByUserIdAndOptionId(int userId,int optionId);

    Optional<Cart> deleteByUserId(int userId);

}
