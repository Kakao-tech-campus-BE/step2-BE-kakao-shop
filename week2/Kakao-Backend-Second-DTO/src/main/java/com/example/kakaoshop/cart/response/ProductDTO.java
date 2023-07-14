package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProductDTO {
    private Long id;
    private String productName;
    private List<CartItemDTO> cartItems;

    @Builder
    public ProductDTO(Long id, String productName, List<CartItemDTO> cartItems) {
        this.id = id;
        this.productName = productName;
        this.cartItems = cartItems;
    }
}
