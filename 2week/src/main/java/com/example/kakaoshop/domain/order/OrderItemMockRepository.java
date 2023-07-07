package com.example.kakaoshop.domain.order;

import com.example.kakaoshop.domain.order.item.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemMockRepository {

  void saveAll(List<OrderItem> orderItems) {
    // fake save
  }
}
