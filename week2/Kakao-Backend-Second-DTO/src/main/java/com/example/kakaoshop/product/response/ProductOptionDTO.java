package com.example.kakaoshop.product.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ProductOptionDTO {
    final private int id;
    final private String optionName;
    final private int price;

    @Builder
    public ProductOptionDTO(int id, String optionName, int price) {
        this.id = id;
        this.optionName = optionName;
        this.price = price;
    }
}
