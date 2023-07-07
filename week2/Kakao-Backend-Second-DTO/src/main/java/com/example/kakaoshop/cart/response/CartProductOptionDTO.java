package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartProductOptionDTO {

    private int id;
    private String optionName;
    private int price;

    @Builder
    public CartProductOptionDTO(int id, String optionName, int price) {
        this.id = id;
        this.optionName = optionName;
        this.price = price;
    }

}
