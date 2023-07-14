package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderRespFindByIdDTO {
    private int id;
    private List<OrderItemDTO> products;
    private int totalPrice;

    @Builder
    public OrderRespFindByIdDTO(int id, List<OrderItemDTO> products, int totalPrice){
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
