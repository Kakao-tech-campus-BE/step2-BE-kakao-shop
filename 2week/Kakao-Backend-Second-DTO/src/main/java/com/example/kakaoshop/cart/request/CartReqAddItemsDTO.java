package com.example.kakaoshop.cart.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartReqAddItemsDTO {

    private int optionId;
    private int quantity;

    @Builder
    public CartReqAddItemsDTO(int optionId, int quantity) {
        this.optionId = optionId;
        this.quantity = quantity;
    }

}
