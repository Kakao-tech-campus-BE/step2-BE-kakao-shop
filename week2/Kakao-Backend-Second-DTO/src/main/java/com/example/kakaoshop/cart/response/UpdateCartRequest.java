package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartRequest {
    private int cartId;
    private int quantity;

    @Builder
    public UpdateCartRequest(int cartId, int quantity) {
        this.cartId = cartId;
        this.quantity = quantity;
    }
}
