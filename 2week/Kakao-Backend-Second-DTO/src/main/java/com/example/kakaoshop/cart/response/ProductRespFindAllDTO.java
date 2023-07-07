package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProductRespFindAllDTO {
    private int id;
    private String productName;
    private List<CartItemRespFindAllDTO> carts;

    @Builder
    public ProductRespFindAllDTO(int id, String productName, List<CartItemRespFindAllDTO> carts) {
        this.id = id;
        this.productName = productName;
        this.carts = carts;
    }
}
