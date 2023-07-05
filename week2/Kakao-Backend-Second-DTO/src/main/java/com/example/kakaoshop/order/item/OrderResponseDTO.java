package com.example.kakaoshop.order.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderResponseDTO {
    private int id;
    private List<OrderProducts> products;
    private int totalPrice;

    @Builder
    public OrderResponseDTO(int id, List<OrderProducts> products, int totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
