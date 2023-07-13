package com.example.kakaoshop.cart.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartAddDTO {
    private int optionId;
    private int quantity;

    @Builder
    public CartAddDTO (int optionId, int quantity ){
        this.optionId=optionId;
        this.quantity=quantity;
    }
}
