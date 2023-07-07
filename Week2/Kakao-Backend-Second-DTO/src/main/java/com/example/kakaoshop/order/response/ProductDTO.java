package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProductDTO {
    private String productName;
    private List<OrderItemDTO> Items;

    @Builder
    public ProductDTO(String productName, List<OrderItemDTO> Items) {
        this.productName = productName;
        this.Items = Items;
    }
}
