package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRespOrderFindByIdDTO {
    private String productName;
    private List<OrderItemRespFindByIdDTO> items;

    @Builder
    public ProductRespOrderFindByIdDTO(String productName, List<OrderItemRespFindByIdDTO> items) {
        this.productName = productName;
        this.items = items;
    }
}