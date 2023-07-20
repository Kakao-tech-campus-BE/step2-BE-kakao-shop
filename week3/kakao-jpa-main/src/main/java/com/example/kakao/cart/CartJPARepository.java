package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    @Query("select c from Cart c where c.user.id = :userId")
    List<Cart> mFindByUserId(@Param("userId") int userId);

    //Cart findById(@Param("id")int cartId);

    @Modifying
    @Query("delete from Cart c where c.user.id = :userId")
    void deleteByUserId(@Param("userId") int userId);

    @Modifying
    @Query("delete from Cart c where c.id = :cartId")
    void deleteByCartId(@Param("cartId") int cartId);
}
