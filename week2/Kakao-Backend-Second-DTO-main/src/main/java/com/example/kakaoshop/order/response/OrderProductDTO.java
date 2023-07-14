package com.example.kakaoshop.order.response;

import com.example.kakaoshop.order.item.OrderItemDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderProductDTO {
    private String productName;
    private List<OrderItemDTO> orderItems;

    @Builder
    public OrderProductDTO(String productName, List<OrderItemDTO> orderItems) {
        this.productName = productName;
        this.orderItems = orderItems;
    }
}
