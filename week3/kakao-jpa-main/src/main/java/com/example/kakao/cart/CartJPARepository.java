package com.example.kakao.cart;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @EntityGraph(attributePaths = {"option", "user", "option.product"})
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    List<Cart> findByUserId(@Param("userId") int userId);

    @EntityGraph(attributePaths = {"option", "user", "option.product"})
    @Query("SELECT c FROM Cart c WHERE c.id = :id")
    Optional<Cart> findByCartId(@Param("id") int id);
}
