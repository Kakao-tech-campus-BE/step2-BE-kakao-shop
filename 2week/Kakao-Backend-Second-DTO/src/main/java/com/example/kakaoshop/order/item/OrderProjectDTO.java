package com.example.kakaoshop.order.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderProjectDTO {
    private String productName;
    private List<OrderItemDTO> orderItemDTO;

    @Builder
    public OrderProjectDTO(String productName, List<OrderItemDTO> orderItemDTO)
    {
        this.productName = productName;
        this.orderItemDTO = orderItemDTO;
    }

}
