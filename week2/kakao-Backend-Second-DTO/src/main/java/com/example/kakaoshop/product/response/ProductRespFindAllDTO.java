package com.example.kakaoshop.product.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductRespFindAllDTO {

    private Long id;
    private String productName;
    private String description;
    private String image;
    private int price;

    @Builder
    public ProductRespFindAllDTO(Long id, String productName, String description, String image, int price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
    }
}
