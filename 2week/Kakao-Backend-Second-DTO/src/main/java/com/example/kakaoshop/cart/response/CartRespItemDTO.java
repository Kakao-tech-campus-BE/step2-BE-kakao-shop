package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartRespItemDTO {
    private int id;
    private String optionName;
    private int quantity;
    private int price;

    @Builder
    public CartRespItemDTO(int id, String optionName, int quantity, int price) {
        this.id = id;
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;
    }
}
