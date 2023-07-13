package com.example.kakaoshop.cart.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemUpdateResDTO {

    private Long cartId;
    private Long optionId;
    private String optionName;
    private Long quantity;
    private Long price;
}
