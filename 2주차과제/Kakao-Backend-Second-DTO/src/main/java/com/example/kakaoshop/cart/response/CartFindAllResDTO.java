package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartFindAllResDTO {
    private List<CartProductDTO> products;
    private int totalPrice;

    @Builder
    public CartFindAllResDTO(List<CartProductDTO> products, int totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
