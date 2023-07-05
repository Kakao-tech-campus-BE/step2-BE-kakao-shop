package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartUpdateItemDTO {
    private int quantity;
    private int price;
    private int cartId;
    private int optionId;
    private String optionName;

    @Builder
    public CartUpdateItemDTO(int quantity, int price, int cartId, int optionId, String optionName) {
        this.quantity = quantity;
        this.price = price;
        this.cartId = cartId;
        this.optionId = optionId;
        this.optionName = optionName;
    }
}
