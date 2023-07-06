package com.example.kakaoshop.order.response;

import lombok.Builder;

import java.util.List;

public class ProductDTO {
    private int id;
    private String productName;
    private List<OrderItemDTO> orderItems;

    @Builder
    public ProductDTO(int id, String productName, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.productName = productName;
        this.orderItems = orderItems;
    }
}
