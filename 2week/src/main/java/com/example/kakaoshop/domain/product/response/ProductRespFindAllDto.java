package com.example.kakaoshop.domain.product.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductRespFindAllDto {

    private int id;
    private String productName;
    private String description;
    private String image;
    private int price;

    @Builder
    public ProductRespFindAllDto(int id, String productName, String description, String image, int price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
    }
}
