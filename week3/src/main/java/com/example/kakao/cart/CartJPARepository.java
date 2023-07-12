package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
	// findByUserId_select_cart_lazy_error_fix_test
	List<Cart> findByUserId(@Param("userId") int userId);
}
