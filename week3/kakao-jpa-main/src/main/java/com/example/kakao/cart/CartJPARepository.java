package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    @Query("select c from Cart c join fetch c.user")
    List<Cart> findAllJoinFetchUser();

    @Query("select c from Cart c join fetch c.user join fetch c.option")
    List<Cart> findAllJoinFetchUserOption();

    @Query("select c from Cart c join fetch c.user join fetch c.option o join fetch o.product")
    List<Cart> findAllJoinFetchAll();

    List<Cart> findByUserId(@Param("userId") int userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Cart c set c.quantity = :#{#cart.quantity}, c.price = :#{#cart.price} where c.id = :#{#cart.id}")
    void update(@Param("cart") Cart cart);
}
