package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class CartRespFindAllDTO {
    final private List<ProductDTO> products;
    final private int totalPrice;

    @Builder
    public CartRespFindAllDTO(List<ProductDTO> products, int totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
