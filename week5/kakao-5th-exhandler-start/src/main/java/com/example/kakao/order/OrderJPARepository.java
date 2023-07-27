package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    @Query("select i from Item i join fetch i.order where i.order.user.id = :orderNumber")
    List<Item> findByOrderNumber(@Param("orderNumber") int orderNumber);
}
