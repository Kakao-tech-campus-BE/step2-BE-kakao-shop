package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class OrderProductDTO {
    private String productName ;
    private List<OrderItemDTO> items ;

    @Builder
    public OrderProductDTO(String productName , List<OrderItemDTO> items) {
        this.items = items;
        this.productName = productName;
    }
}
