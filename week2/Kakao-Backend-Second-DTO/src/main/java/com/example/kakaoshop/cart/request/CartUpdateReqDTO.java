package com.example.kakaoshop.cart.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartUpdateReqDTO {
    private int cartId;
    private int quantity;

    @Builder
    public CartUpdateReqDTO(int optionId, int quantity){
        this.cartId = cartId;
        this.quantity = quantity;
    }
}