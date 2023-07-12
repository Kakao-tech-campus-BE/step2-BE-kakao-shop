package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemInfoDTO {
    private Long cartId;
    private Long optionId;
    private String optionName;
    private int quantity;
    private int price;

    @Builder
    public CartItemInfoDTO(Long cartId, Long optionId, String optionName, int quantity, int price) {
        this.cartId = cartId;
        this.optionId = optionId;
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;
    }
}
