package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartDTO{

    private int optionId;
    private int quantity;

    @Builder
    public CartDTO(int optionId, int quantity) {
        this.optionId = optionId;
        this.quantity = quantity;
    }
}