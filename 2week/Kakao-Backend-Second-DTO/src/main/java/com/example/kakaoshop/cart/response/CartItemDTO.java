package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemDTO {

    private int id;
    private ProductOptionDTO option;
    private int quantity;
    private int price;

    @Builder
    public CartItemDTO(int id, ProductOptionDTO option, int quantity, int price) {
        this.id = id;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
    }
}
