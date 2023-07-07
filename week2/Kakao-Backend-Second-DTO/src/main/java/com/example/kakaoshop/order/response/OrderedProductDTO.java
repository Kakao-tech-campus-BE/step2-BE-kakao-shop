package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderedProductDTO {
    private String productName;
    private List<OrderedItemDTO> orderedItems;

    @Builder
    public OrderedProductDTO(String productName, List<OrderedItemDTO> orderedItems){
        this.productName = productName;
        this.orderedItems = orderedItems;
    }
}
