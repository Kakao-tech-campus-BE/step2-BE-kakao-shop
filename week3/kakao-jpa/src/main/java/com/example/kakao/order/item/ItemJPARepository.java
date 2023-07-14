package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {
//    @Query("select i from Item i join fetch i.order join fetch i.option " +
//            "where i.id = :id")
    Optional<Item> findById(int id);

    @Query("select i from Item i join fetch i.order join fetch i.option " +
            "where i.order.id = :orderId")
    List<Item> findByOrderId(@Param("orderId")int orderId);

}
