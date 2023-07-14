package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    //Optional<Cart> findById(int id);
    @Query ("select c from Cart c " +
            "join fetch c.user " +
            "join fetch c.option o " +
            "join fetch o.product " +
            "where c.user.id = :userId ")
    Optional<List<Cart>>findbyUserId(@Param("userId") int userId);
}
