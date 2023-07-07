package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CartProductUpdateDTO {
    private List<CartItemUpdateDTO> cartItems;

    @Builder
    public CartProductUpdateDTO(List<CartItemUpdateDTO> cartItems){
        this.cartItems = cartItems;
    }
}
