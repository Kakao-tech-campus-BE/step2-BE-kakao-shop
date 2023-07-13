package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartProductOptionUpdateDTO {
    private int id;
    private String optionName;

    @Builder
    public CartProductOptionUpdateDTO(int id, String optionName){
        this.id = id;
        this.optionName = optionName;
    }
}
