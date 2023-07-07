package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartResUpdateDTO {
    private int totalPrice;
    private List<ProductDTO> products;

    @Builder
    public CartResUpdateDTO(int totalPrice, List<ProductDTO> products) {
        this.totalPrice = totalPrice;
        this.products = products;
    }
}
