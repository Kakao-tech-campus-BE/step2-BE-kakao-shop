package com.example.kakaoshop.product.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductOptionDTO {

    private int id;
    private String optionName;
    private int price;

    @Builder
    public ProductOptionDTO(int id, String optionName, int price) {
        this.id = id;
        this.optionName = optionName;
        this.price = price;
    }
}
