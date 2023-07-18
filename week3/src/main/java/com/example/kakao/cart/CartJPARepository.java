package com.example.kakao.cart;

import com.example.kakao.product.option.Option;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c join fetch c.user join fetch c.option")
    List<Cart> mFindFetchAll();

    @Query("select c from Cart c join fetch c.user join fetch c.option where c.id = :cartId")
    Optional<Cart> mFindById(@Param("cartId") int cartId);

}
