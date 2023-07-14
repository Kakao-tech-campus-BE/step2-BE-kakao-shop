package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdatedCartItemDTO {
    final private int cartId;
    final private int optionId;
    final private String optionName;
    final private int quantity;
    final private int price;

    @Builder
    public UpdatedCartItemDTO(int cartId, int optionId, String optionName, int quantity, int price) {
        this.cartId = cartId;
        this.optionId = optionId;
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;
    }
}
