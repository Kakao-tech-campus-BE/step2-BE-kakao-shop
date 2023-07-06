package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Data;

@Data
public class CartProductDTO {

    Long cartId;
    Long optionId;
    String optionName;
    Integer quantity;
    Integer price;

    @Builder
    public CartProductDTO(Long cartId, Long optionId, String optionName, Integer quantity, Integer price) {
        this.cartId = cartId;
        this.optionId = optionId;
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;
    }
}
