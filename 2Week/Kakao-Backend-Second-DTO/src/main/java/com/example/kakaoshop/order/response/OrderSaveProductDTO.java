package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderSaveProductDTO {
    private String productName;
    private List<OrderSaveItemDTO> items;

    @Builder
    public OrderSaveProductDTO(String productName, List<OrderSaveItemDTO> orderItems) {
        this.productName = productName;
        this.items = orderItems;
    }
}
