package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CartDTO {
    private int cartId;
    private int optionId;
    private String optionName;
    private int quantity;
    private int price;

    @Builder
    public CartDTO(int cartId, int optionId, String optionName, int quantity, int price){
        this.cartId = cartId;
        this.optionId = optionId;
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;
    }
}
