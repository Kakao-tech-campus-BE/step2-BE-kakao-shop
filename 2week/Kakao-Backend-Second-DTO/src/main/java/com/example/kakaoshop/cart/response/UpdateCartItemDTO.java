package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateCartItemDTO {
    private int cartId;
    private int optionId;
    private String optionName;
    private int quantity;
    private long price;

    @Builder
    public UpdateCartItemDTO(int cartId, int optionId, String optionName, int quantity, long price ){
        this.cartId = cartId;
        this.optionId = optionId;
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;
    }
}
