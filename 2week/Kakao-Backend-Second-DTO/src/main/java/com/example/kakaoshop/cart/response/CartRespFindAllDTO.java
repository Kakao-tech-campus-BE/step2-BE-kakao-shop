package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartRespFindAllDTO {
    private List<CartItemDTO> products;
    private int totalPrice;

    @Builder
    public CartRespFindAllDTO(List<CartItemDTO> products, int totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
