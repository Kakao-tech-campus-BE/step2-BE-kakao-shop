package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemResponseDTO {

    private Integer cartId;
    private int optionId;
    private String optionName;
    private int quantity;
    private int price;

    @Builder
    public CartItemResponseDTO(Integer cartId, int optionId, String optionName, int quantity, int price) {
        this.cartId = cartId;
        this.optionId = optionId;
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;
    }
}
