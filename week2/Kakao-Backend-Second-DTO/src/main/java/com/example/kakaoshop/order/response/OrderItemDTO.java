package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderItemDTO {
    private int id;
    private String productName;
    private List<ItemOptionDTO> items;

    @Builder
    public OrderItemDTO(int id, String productName, List<ItemOptionDTO> items) {
        this.id = id;
        this.productName = productName;
        this.items = items;
    }
}