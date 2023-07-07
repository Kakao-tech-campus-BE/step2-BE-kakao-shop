package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartRespFindAllDTO {
    private List<ProductRespFindAllDTO> products;
    private int totalPrice;

    @Builder
    public CartRespFindAllDTO(List<ProductRespFindAllDTO> products, int totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
