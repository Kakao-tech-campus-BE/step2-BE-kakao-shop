package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsertCartRequest {
    private int optionId;
    private int quantity;

    @Builder
    public InsertCartRequest(int optionId, int quantity) {
        this.optionId = optionId;
        this.quantity = quantity;
    }
}
