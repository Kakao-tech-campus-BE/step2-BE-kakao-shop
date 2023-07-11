package com.example.kakaoshop.cart.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartReqAddDTO {

    private int optionId;
    private int quantity;

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
