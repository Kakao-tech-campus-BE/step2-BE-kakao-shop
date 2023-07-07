package com.example.kakaoshop.cart.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartReqAddDTO {
    private int optionId;
    private int quantity;

    public CartReqAddDTO(int optionId, int quantity) {
        this.optionId=optionId;
        this.quantity=quantity;
    }
}
