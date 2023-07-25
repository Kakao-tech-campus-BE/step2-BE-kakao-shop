package com.example.kakaoshop.order.domain.model;

import com.example.kakaoshop.cart.domain.model.OrderDetail;
import lombok.Builder;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderCashier {

    private final List<OrderItem> orderItems;

    @Builder
    public OrderCashier(List<OrderItem> orderItems) {
        this.orderItems=orderItems;
    }

    private List<OrderDetail> getOrderDetails() {
        return orderItems.stream()
                .map(o -> OrderDetail.newInstance(o.getPrice(), o.getQuantity()))
                .collect(Collectors.toList());
    }

    public int calculateTotalPrice() {
        return getOrderDetails()
                .stream()
                .mapToInt(OrderDetail::calculate)
                .sum();
    }
}
