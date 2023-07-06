package com.example.kakaoshop.order.response;

import lombok.Builder;

import java.util.List;

public class OrderDTO {
    private int id;
    private List<OrderProductDTO> orders;
    private int totalPrice;

    @Builder
    public OrderDTO(int id, List<OrderProductDTO> orders, int totalPrice) {
        this.id = id;
        this.orders = orders;
        this.totalPrice = totalPrice;
    }
}
