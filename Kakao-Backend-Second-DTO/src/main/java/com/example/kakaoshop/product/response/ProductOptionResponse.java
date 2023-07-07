package com.example.kakaoshop.product.response;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductOptionResponse {

    private int id;
    private String optionName;
    private int price;
}
