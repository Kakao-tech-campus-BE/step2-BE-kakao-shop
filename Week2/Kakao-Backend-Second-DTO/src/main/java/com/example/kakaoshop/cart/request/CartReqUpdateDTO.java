package com.example.kakaoshop.cart.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartReqUpdateDTO {

    private int cartId;
    private int quantity;

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
