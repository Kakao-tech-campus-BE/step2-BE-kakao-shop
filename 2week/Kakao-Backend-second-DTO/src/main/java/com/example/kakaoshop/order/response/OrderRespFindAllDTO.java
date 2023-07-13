package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderRespFindAllDTO {
    private int id;
    private OrderDTO products;
    private int totalPrice;

    @Builder()
    public OrderRespFindAllDTO(int id, OrderDTO products, int totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}