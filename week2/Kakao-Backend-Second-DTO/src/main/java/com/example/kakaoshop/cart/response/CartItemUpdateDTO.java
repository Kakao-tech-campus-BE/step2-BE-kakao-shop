package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItemUpdateDTO {
    private int id;
    private CartProductOptionUpdateDTO option;
    private int quantity;
    private long price;


    @Builder
    public CartItemUpdateDTO(int id,  CartProductOptionUpdateDTO option, int quantity, long price){
        this.id = id;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
    }
}
