package com.example.kakaoshop.product.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProductRespFindByIdDTO {
    private int id;
    private String productName;
    private String description;
    private String image;
    private int price;
    private List<ProductOptionDTO> options;

    @Builder
    public ProductRespFindByIdDTO(int id, String productName, String description, String image, int price, List<ProductOptionDTO> options) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
        this.options = options;
    }
}
