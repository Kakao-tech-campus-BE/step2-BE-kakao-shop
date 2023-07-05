package com.example.kakaoshop.product.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ProductRespFindAllDTO {
    final private int id;
    final private String productName;
    final private String description;
    final private String image;
    final private int price;

    @Builder
    public ProductRespFindAllDTO(int id, String productName, String description, String image, int price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
    }
}
