package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CartRespUpdateDTO {
    private List<CartRespCartDTO> carts;
    private int totalPrice;

    @Builder
    public CartRespUpdateDTO(List<CartRespCartDTO> carts, int totalPrice) {
        this.carts= carts;
        this.totalPrice = totalPrice;
    }
}

