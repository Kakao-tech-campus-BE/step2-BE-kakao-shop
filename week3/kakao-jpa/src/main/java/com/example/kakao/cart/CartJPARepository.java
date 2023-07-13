package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c " +
            "join fetch c.user " +
            "where c.user.id = :userId")
    Optional<List<Cart>> findByUserId(@Param("userId") int userId);
}