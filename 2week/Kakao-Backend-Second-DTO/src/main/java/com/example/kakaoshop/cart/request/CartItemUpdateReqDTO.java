package com.example.kakaoshop.cart.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemUpdateReqDTO {
    private int cartId;
    private int quantity;

    @Builder
    public CartItemUpdateReqDTO(int cartId, int quantity) {
        this.cartId = cartId;
        this.quantity = quantity;
    }
}
