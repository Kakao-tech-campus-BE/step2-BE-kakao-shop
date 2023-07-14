package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemReqDTO {
    private int id;
    private int quantity;

    @Builder
    public CartItemReqDTO(int id, int quantity){
        this.id = id;
        this.quantity = quantity;
    }
}
