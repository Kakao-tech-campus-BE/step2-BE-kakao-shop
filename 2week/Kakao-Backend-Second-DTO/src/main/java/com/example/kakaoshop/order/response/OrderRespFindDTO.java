package com.example.kakaoshop.order.response;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderRespFindDTO {
    private int id;
    private List<OrderRespProductDTO> products;
    private int totalPrice;

    @Builder
    public OrderRespFindDTO(int id, List<OrderRespProductDTO> products, int totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice=totalPrice;
    }
}
