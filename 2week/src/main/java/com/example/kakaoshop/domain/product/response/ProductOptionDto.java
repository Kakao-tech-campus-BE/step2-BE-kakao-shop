package com.example.kakaoshop.domain.product.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductOptionDto {

    private int id;
    private String optionName;
    private int price;

    @Builder
    public ProductOptionDto(int id, String optionName, int price) {
        this.id = id;
        this.optionName = optionName;
        this.price = price;
    }
}
