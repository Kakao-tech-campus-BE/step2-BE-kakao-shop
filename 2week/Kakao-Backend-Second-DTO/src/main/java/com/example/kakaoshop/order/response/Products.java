package com.example.kakaoshop.order.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Products {

    private String productName;
    private List<com.example.kakaoshop.order.response.ProductOptionDTO> items;

    @Builder
    public Products(String productName, List<ProductOptionDTO> items) {
        this.productName = productName;
        this.items = items;
    }
}
