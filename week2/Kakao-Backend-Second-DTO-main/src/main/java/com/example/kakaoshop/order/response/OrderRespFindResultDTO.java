package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderRespFindResultDTO {
    private int id;
    private List<OrderProductItemDTO> products;
    private int totalPrice;

    @Builder
    public OrderRespFindResultDTO(int id, List<OrderProductItemDTO> products, int totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}