package com.example.kakaoshop.cart.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartUpdateRequestDTO {

    private int cartId;
    private int quantity;
    @Builder
    public CartUpdateRequestDTO(int cartId, int quantity) {
        this.cartId = cartId;
        this.quantity = quantity;
    }
}
