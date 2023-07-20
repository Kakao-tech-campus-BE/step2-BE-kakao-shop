package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    @Query("select c from Cart c join fetch c.option where c.id = :cartId")
    Optional<Cart> mFindById(@Param("cartId") int cartId);

    @Query("select c from Cart c join fetch c.option where c.user.id = :userId")
    List<Cart> mFindByUserId(@Param("userId") int userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Cart c set c.quantity = :#{#cart.quantity}, c.price = :#{#cart.price} where c.id = :#{#cart.id}")
    void update(@Param("cart") Cart cart);
}
