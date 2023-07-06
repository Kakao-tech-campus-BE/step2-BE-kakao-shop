package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CartRespProductDTO {
    private String productName;
    private List<CartRespItemDTO> items;

    @Builder
    public CartRespProductDTO(String productName, List<CartRespItemDTO> items) {
        this.productName = productName;
        this.items = items;
    }
}
