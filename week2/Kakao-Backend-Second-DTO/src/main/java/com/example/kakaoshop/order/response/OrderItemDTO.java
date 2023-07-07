package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItemDTO {
    private int id;
    private String optionName;
    private int quantity;
    private long price;

    @Builder
    public OrderItemDTO(int id, String optionName, int quantity, long price){
        this.id = id;
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;
    }

}
