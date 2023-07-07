package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderProductDTO {
    private String productName;
    List<OrderItemDTO> orderItemList;

    @Builder
    public OrderProductDTO(String productName, List<OrderItemDTO> orderItemList){
        this.productName = productName;
        this.orderItemList = orderItemList;
    }
}
