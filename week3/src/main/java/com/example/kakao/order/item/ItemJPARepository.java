package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

	@Query("select i from Item i join fetch i.order where i.order.id = :orderId")
	List<Item> findByOrderId(int orderId);



}
