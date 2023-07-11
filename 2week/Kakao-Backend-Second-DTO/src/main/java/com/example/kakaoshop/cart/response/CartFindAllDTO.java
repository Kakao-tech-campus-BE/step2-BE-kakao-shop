package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartFindAllDTO {
    private List<ProductDTO> products;
    private int totalPrice;

    @Builder
    public CartFindAllDTO(List<ProductDTO> products, int totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
