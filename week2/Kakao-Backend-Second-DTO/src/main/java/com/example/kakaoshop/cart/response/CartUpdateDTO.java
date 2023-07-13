package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartUpdateDTO {

    private Long cartId;

    private Long optionId;

    private String optionName;

    private int quantity;

    private int price;

    @Builder
    public CartUpdateDTO(Long cartId, Long optionId, String optionName, int quantity, int price) {
        this.cartId = cartId;
        this.optionId = optionId;
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;
    }
}
