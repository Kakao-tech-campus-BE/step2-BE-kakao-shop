package com.example.kakaoshop.order.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProductDTO {
    private String productName;
    private List<OrderItem> items;

    @Builder
    public ProductDTO(String productName, List<OrderItem> items) {
        this.productName = productName;
        this.items = items;
    }
}
