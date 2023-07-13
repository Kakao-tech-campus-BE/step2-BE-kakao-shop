package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CartUpdateRespDTO {
    private List<CartProductUpdateDTO> products;
    private long totalPrice;

    @Builder
    public CartUpdateRespDTO(List<CartProductUpdateDTO> products, long totalPrice){
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
