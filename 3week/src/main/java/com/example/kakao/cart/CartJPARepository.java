package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c join fetch c.user join fetch c.option where c.user.id = :userId")
    List<Cart> mFindByUserId(@Param("userId") int userId);

    @Query("select c from Cart c join fetch c.user join fetch c.option")
    List<Cart> mFindAll();

    @Query("select c from Cart c join fetch c.user join fetch c.option where c.id = :cartId")
    Optional<Cart> mFindById(@Param("cartId") int cartId);
}
