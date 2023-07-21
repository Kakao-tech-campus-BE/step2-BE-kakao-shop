package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {


    @Query("SELECT DISTINCT C FROM Cart C JOIN FETCH C.option WHERE C.user.email = :email")
    public List<Cart> findAllWithOptionsUsingFetchJoinByEmail(@Param("email") String email);

    @Query("SELECT DISTINCT C FROM Cart C JOIN C.option WHERE C.user.email = :email")
    public List<Cart> findAllWithOptionsUsingJoinByEmail(@Param("email") String email);

    @Query("SELECT DISTINCT C FROM Cart C JOIN fETCH C.option WHERE C.id = :cartId")
    public Optional<Cart> findCartWithOptionUsingFetchJoinById(@Param("cartId") Integer cartId);

}
