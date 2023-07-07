package com.example.kakaoshop.cart.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartReqUpdateDTO {

    private int cartId;
    private int quantity;

    public CartReqUpdateDTO(int cartId, int quantity) {
        this.cartId=cartId;
        this.quantity=quantity;
    }
}
