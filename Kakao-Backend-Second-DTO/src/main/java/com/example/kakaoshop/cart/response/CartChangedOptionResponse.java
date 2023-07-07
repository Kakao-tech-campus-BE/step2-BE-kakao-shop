package com.example.kakaoshop.cart.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CartChangedOptionResponse {

    private Long cartId;
    private Long optionId;
    private String optionName;
    private Integer quantity;
    private Integer price;

}
