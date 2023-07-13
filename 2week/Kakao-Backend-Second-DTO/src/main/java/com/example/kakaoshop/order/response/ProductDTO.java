package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProductDTO {
    private String productName;
    private List<CartItemDTO> items;

    @Builder
    public ProductDTO(String productName, List<CartItemDTO> items) {
        this.productName = productName;
        this.items = items;
    }
}
