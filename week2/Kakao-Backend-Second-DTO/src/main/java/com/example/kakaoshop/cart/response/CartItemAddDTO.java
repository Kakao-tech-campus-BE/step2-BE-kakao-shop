package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CartItemAddDTO {
    private int optionId;
    private int quantity;

    @Builder
    public CartItemAddDTO(int optionId, int quantity){
        this.optionId = optionId;
        this.quantity = quantity;
    }
}
