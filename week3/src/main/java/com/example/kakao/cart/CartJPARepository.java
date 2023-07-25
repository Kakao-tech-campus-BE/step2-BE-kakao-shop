package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {


	List<Cart> findByUserId(@Param("userId") int userId);

	// 한 방 쿼리를 사용한 방법
	@Query("SELECT c FROM Cart c JOIN FETCH c.user u JOIN FETCH c.option o")
	List<Cart> findAllWithUserAndOption();


}
