package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

  @Query("select i from Item  i join fetch  i.order join fetch i.option o join fetch o.product p where i.order.id = :orderId order by i.order.id asc")
  List<Item> findByOrderId(@Param("orderId") int orderId);

}
