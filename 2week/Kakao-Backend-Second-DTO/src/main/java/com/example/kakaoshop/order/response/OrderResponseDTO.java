package com.example.kakaoshop.order.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class OrderResponseDTO {
    private int orderId;
    private List<OrderProductDTO> products;
    private int totalPrice;

    @Builder
    public OrderResponseDTO(int orderId, List<OrderProductDTO> products, int totalPrice) {
        this.orderId = orderId;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
