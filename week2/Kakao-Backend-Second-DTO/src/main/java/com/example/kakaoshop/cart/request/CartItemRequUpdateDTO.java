package com.example.kakaoshop.cart.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartItemRequUpdateDTO {

    private List<CartItemUpdateDTO> cartItems;

    @Builder
    public CartItemRequUpdateDTO(List<CartItemUpdateDTO> cartItems) {
        this.cartItems = cartItems;
    }
}
