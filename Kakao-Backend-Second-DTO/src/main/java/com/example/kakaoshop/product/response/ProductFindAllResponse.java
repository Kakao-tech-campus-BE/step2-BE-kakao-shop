package com.example.kakaoshop.product.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductFindAllResponse {

    private int id;
    private String productName;
    private String description;
    private String image;
    private int price;
}
