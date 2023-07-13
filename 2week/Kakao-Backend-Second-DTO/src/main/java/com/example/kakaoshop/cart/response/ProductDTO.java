package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProductDTO {
    private int id;
    private String productName;
    private List<CartOptionDTO> cartItems;

    @Builder
    public ProductDTO(int id, String productName, List<CartOptionDTO> cartItems) {
        this.id = id;
        this.productName = productName;
        this.cartItems = cartItems;
    }
}
