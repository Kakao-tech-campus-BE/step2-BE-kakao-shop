package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    // 장바구니 조회
    List<Cart> findByUserId(int userId);
    // 장바구니 업데이트 - findById

    // 결제하기(장바구니 삭제)
    void deleteByUserId(int userId);
}
