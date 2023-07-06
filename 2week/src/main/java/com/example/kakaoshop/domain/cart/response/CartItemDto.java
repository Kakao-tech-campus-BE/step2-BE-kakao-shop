package com.example.kakaoshop.domain.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemDto {

    private int id;
    private ProductOptionDto option;
    private int quantity;
    private int price;

    @Builder
    public CartItemDto(int id, ProductOptionDto option, int quantity, int price) {
        this.id = id;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
    }

}
