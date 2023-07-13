package com.example.kakaoshop.cart.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemUpdateDTO {
    private Integer cartId;
    private int quantity;

    @Builder
    public CartItemUpdateDTO(Integer cartId, int quantity) {
        this.cartId = cartId;
        this.quantity = quantity;
    }
}
