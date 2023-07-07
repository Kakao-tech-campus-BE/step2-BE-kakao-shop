package com.example.kakaoshop.product.response;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductFindByIdResponse {

    private int id;
    private String productName;
    private String description;
    private String image;
    private int price;
    private int starCount;
    private List<ProductOptionResponse> options;
}
