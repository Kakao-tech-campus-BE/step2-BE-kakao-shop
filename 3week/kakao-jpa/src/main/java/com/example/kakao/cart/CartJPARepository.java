package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
	
	@Query("SELECT c FROM Cart c JOIN FETCH c.option o JOIN FETCH o.product JOIN FETCH c.user WHERE c.user.id = :userId")
	List<Cart> findByUserId(@Param("userId") int userId);
	
}
