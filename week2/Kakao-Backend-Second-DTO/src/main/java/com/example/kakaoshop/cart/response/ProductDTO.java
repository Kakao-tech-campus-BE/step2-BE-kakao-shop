package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProductDTO {
    private int id;
    private String productName;
    private List<CartItemDTO> carts;

    @Builder
    public ProductDTO(int id, String productName, List<CartItemDTO> carts) {
        this.id = id;
        this.productName = productName;
        this.carts = carts;
    }
}
