package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsertCartCommand {
    final private int optionId;
    final private int quantity;

    @Builder
    public InsertCartCommand(int optionId, int quantity) {
        this.optionId = optionId;
        this.quantity = quantity;
    }
}
