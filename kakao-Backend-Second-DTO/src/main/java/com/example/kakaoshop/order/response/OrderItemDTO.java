package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.util

@Getter @Setter
public class OrderItemDTO {
    private int id;
    private String optionName;
    private int quantity;
    private int Price;

    @Builder
    public OrderDTO(int id, String optionName, int quantity, int Price) {
        this.id = id;
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;
    }
}
