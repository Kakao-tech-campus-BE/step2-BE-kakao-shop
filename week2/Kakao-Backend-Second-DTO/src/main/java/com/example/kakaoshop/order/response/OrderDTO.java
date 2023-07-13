package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderDTO {
    private int id;
    private List<OrderProductDTO> products;
    private long totalPrice;

    @Builder
    public OrderDTO(int id, long totalPrice, List<OrderProductDTO> products){
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
