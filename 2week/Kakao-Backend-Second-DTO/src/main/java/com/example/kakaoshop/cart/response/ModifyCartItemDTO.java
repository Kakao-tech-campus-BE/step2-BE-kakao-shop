package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyCartItemDTO {

    private int cartId;

    private int quantity;

    public ModifyCartItemDTO(){

    }

    @Builder
    public ModifyCartItemDTO(int cartId, int quantity) {
        this.cartId = cartId;
        this.quantity = quantity;
    }
}
