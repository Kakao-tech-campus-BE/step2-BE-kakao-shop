package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItemDTO {
    final private int id;
    final private String optionName;
    final private int quantity;
    final private int price;

    @Builder
    public OrderItemDTO(int id, String optionName, int quantity, int price) {
        this.id = id;
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;
    }
}
