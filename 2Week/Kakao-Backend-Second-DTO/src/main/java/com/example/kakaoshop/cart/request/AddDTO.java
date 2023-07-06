package com.example.kakaoshop.cart.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddDTO {
    private String optionId;
    private String quantity;

    public AddDTO(String optionId, String quantity) {
        this.optionId = optionId;
        this.quantity = quantity;
    }
}
