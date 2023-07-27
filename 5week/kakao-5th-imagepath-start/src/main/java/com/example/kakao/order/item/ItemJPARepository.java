package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

	// Item.getOption을 하면서 생기는 N+1문제를 해결하기 위해 한방 쿼리를 작성하였다.
	@Query("SELECT i FROM Item i JOIN FETCH i.option o JOIN FETCH o.product WHERE i.id = :orderId")
	List<Item> findAllByOrderId(@Param("orderId") int id);

}
