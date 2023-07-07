package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartRespUpdateDTO {
    private List<CartItemUpdateDTO> cartItems;
    private int totalPrice;

    @Builder
    public CartRespUpdateDTO(List<CartItemUpdateDTO> cartItems, int totalPrice) {
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
    }
}
