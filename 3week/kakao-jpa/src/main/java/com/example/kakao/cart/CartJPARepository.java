package com.example.kakao.cart;

import com.example.kakao.product.option.ProductOption;
import com.example.kakao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// @Transactional 추가
@Transactional
public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    // 장바구니 보기
    List<Cart> findByUserId(int userId);


    // 장바구니 담기

    // 장바구니 수정

    // 장바구니 비우기
    @Modifying
    @Query("DELETE FROM Cart c WHERE c.user.id = :userId")
    void deleteByUserId(int userId);

}
