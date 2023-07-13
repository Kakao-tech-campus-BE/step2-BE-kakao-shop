package com.example.kakaoshop.cart.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartReqUpdateItemsDTO {
    private int cartId;
    private int quantity;

    @Builder
    public CartReqUpdateItemsDTO(int cartId, int quantity) {
        this.cartId = cartId;
        this.quantity = quantity;
    }
}
