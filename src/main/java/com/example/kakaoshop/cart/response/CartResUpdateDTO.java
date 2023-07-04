package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartResUpdateDTO {

    private List<CartItemInfoDTO> carts;
    private int totalPrice;

    @Builder
    public CartResUpdateDTO(List<CartItemInfoDTO> carts, int totalPrice) {
        this.carts = carts;
        this.totalPrice = totalPrice;
    }
}
