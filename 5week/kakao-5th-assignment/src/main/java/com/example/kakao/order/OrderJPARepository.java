package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {

    @Query("select i from Item i where i.order.id =: orderId")
    List<Item> findByOrderId(int orderId);
}
