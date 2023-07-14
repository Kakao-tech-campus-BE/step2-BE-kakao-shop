package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderSaveDTO {
    private int id;
    private List<OrderSaveProductDTO> products;
    private int totalPrice;

    @Builder
    public OrderSaveDTO(int id, List<OrderSaveProductDTO> products, int totalPrice){
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
