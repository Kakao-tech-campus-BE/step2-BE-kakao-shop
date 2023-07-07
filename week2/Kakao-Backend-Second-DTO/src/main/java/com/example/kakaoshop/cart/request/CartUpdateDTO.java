package com.example.kakaoshop.cart.request;

import lombok.Builder;

public class CartUpdateDTO {
    private int cartId;
    private int quantity;

    @Builder
    public CartUpdateDTO (int cartId, int quantity) {
        this.cartId = cartId;
        this.quantity = quantity;
    }
}
