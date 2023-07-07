package com.example.kakaoshop.cart.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemReqDTO {
    //Cart pk 매핑
    private int cartId;
    private int quantity;

    @Builder
    public CartItemReqDTO(int cartId, int quantity) {
        this.cartId = cartId;
        this.quantity = quantity;
    }
}
