package com.example.kakaoshop.cart.request;

import lombok.Builder;
import lombok.Data;

@Data
public class CartDTO {

    private Long optionId;
    private Integer quantity;

    @Builder
    public CartDTO(Long optionId, Integer quantity) {
        this.optionId = optionId;
        this.quantity = quantity;
    }
}
