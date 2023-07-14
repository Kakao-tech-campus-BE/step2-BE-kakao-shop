package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("select cart " +
            "from Cart cart " +
            "join fetch cart.user " +
            "join fetch cart.option")
    List<Cart> findAll();

    @Query("select cart " +
            "from Cart cart " +
            "join fetch cart.user " +
            "join  fetch cart.option " +
            "where cart.id = :cartId")
    Optional<Cart> findById(int cartId);

    @Query("select cart " +
            "from Cart cart " +
            "join fetch cart.user " +
            "join fetch cart.option " +
            "where cart.user.id = :id")
    List<Cart> findAllByUserId(int id);

}
