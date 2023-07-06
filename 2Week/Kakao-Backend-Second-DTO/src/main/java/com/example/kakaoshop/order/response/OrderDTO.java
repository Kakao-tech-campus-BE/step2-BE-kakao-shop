package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderDTO {
    private int id;
    private List<OrderProductDTO> products;
    private int totalPrice;

    @Builder
    public OrderDTO(int id, List<OrderProductDTO> orders, int totalPrice) {
        this.id = id;
        this.products = orders;
        this.totalPrice = totalPrice;
    }
}
