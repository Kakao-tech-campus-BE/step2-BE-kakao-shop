package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProductDTO {
    private  int id;
    private String productName;
    private List<OrderItemDTO> items;

    @Builder
    public ProductDTO(int id, String productName, List<OrderItemDTO> orderItems) {
        this.id=id;
        this.productName = productName;
        this.items = orderItems;
    }
}
