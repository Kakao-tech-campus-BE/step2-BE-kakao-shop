package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("select cart " +
            "from Cart cart " +
            "join fetch cart.user " +
            "join fetch cart.option")
    List<Cart> findAllByFetchJoin();

    @Query("select cart " +
            "from Cart cart " +
            "join fetch cart.user " +
            "join  fetch cart.option " +
            "where cart.id = :cartId")
    Cart findById(@Param("cartId") int cartId);



}
