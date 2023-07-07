package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderResultProductDTO {
    private String productName;
    private List<OrderResultItemDTO> items;

    @Builder
    public OrderResultProductDTO(String productName, List<OrderResultItemDTO> orderItems) {
        this.productName = productName;
        this.items = orderItems;
    }
}
