package com.example.kakaoshop.cart.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartItemRequAddDTO {

    private List<CartItemAddDTO> cartItems;

    @Builder
    public CartItemRequAddDTO(List<CartItemAddDTO> cartItems) {
        this.cartItems = cartItems;
    }
}
