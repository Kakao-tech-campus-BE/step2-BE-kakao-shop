package com.example.kakaoshop.domain.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProductDto {
    private int id;
    private String productName;
    private List<CartItemDto> cartItems;

    @Builder
    public ProductDto(int id, String productName, List<CartItemDto> cartItems) {
        this.id = id;
        this.productName = productName;
        this.cartItems = cartItems;
    }
}
