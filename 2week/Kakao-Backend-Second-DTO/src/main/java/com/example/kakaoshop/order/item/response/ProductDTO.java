package com.example.kakaoshop.order.item.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {

    private String productName;
    private List<OptionDTO> items;

    @Builder
    public ProductDTO(String productName, List<OptionDTO> items) {
        this.productName = productName;
        this.items = items;
    }

}
