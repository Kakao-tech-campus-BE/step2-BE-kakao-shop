package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProductRespOrderAddDTO {
    private String productName;
    private List<OrderItemRespAddDTO> items;

    @Builder
    public ProductRespOrderAddDTO(String productName, List<OrderItemRespAddDTO> items) {
        this.productName = productName;
        this.items = items;
    }
}
