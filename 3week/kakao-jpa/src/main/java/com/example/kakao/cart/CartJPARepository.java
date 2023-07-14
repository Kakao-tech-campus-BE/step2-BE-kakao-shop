package com.example.kakao.cart;

import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT c FROM Cart c JOIN FETCH c.user JOIN FETCH c.option WHERE c.id = :cartId")
    List<Cart> findByCartIdWithUserAndOption(@Param("cartId") int cartId); //사용자의 카트들을 불러오는 것이 아닌 id에 맞는 카트만 불러온다.

    @Query("SELECT c FROM Cart c JOIN FETCH c.user JOIN FETCH c.option WHERE c.user.id = :userId")
    List<Cart> findByUserIdWithUserAndOption(@Param("userId") int userId); //사용자의 모든 장바구니를 불러온다.
}
