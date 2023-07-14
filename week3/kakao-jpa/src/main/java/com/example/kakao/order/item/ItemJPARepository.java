package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {
    @Query("select count(*)" +
            "from Item i " +
            "where i.order.user.id = :user_id")
    int countByUserId(@Param("user_id") int user_id);

    @Query("select i " +
            "from Item i " +
            "where i.order.id = :order_id")
    List<Item> findByOrderId(@Param("order_id") int order_id);
}
