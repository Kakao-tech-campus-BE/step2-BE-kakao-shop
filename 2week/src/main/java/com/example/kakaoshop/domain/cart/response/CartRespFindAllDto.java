package com.example.kakaoshop.domain.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartRespFindAllDto {
    private List<ProductDto> products;
    private int totalPrice;

    @Builder
    public CartRespFindAllDto(List<ProductDto> products, int totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
