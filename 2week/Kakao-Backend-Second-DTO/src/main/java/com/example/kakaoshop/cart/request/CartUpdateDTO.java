package com.example.kakaoshop.cart.request;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
@Getter @Setter

public class CartUpdateDTO {
    private int cartId;
    private int quantity;

    @Builder
    public CartUpdateDTO(int cartId, int quantity){
        this.cartId=cartId;
        this.quantity=quantity;
    }
}
