package com.example.kakaoshop.cart.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOptionDTO2 {
    private int optionId;
    private int quantity;
    @Builder
    public ProductOptionDTO2(int optionId, int quantity) {
        this.optionId = optionId;
        this.quantity = quantity;
    }
}
