package com.example.kakaoshop.product.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class ProductRespFindByIdDTO {
    final private int id;
    final private String productName;
    final private String description;
    final private String image;
    final private int price;
    final private int starCount; // 0~5
    final private List<ProductOptionDTO> options;

    @Builder
    public ProductRespFindByIdDTO(int id, String productName, String description, String image, int price, int starCount, List<ProductOptionDTO> options) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
        this.starCount = starCount;
        this.options = options;
    }
}
