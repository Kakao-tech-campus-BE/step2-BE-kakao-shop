package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateCartDTO {

    private int optionId;
    private int quantity;

    @Builder
    public UpdateCartDTO(int optionId, int quantity){
        this.optionId = optionId;
        this.quantity = quantity;
    }
}
