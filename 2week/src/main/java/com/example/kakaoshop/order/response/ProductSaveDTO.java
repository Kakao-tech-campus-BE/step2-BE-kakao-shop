package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProductSaveDTO {
    private String productName;
    private List<OrderItemSaveDTO> items;

    @Builder
    public ProductSaveDTO(String productName, List<OrderItemSaveDTO> items) {
        this.productName = productName;
        this.items = items;
    }
}
