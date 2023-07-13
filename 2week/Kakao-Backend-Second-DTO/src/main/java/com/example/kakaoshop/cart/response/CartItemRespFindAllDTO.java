package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemRespFindAllDTO {

    private int id;
    private ProductOptionRespFindAllDTO option;
    private int quantity;
    private int price;

    @Builder
    public CartItemRespFindAllDTO(int id, ProductOptionRespFindAllDTO option, int quantity, int price) {
        this.id = id;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
    }
}
