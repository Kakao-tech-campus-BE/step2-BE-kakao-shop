package com.example.kakaoshop.order.response;

import lombok.Builder;

import java.util.List;

public class OrderProductDTO {
    private String productName;
    private List<OrderItemDTO> orderItems;

    @Builder
    public OrderProductDTO(String productName, List<OrderItemDTO> orderItems) {
        this.productName = productName;
        this.orderItems = orderItems;
    }
}
