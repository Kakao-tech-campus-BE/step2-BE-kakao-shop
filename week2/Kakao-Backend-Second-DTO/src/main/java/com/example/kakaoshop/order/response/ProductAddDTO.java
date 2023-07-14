package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProductAddDTO {

    private String productName;
    private List<OrderItemAddDTO> items;

    @Builder
    public ProductAddDTO(String productName, List<OrderItemAddDTO> items) {
        this.productName = productName;
        this.items = items;
    }
}
