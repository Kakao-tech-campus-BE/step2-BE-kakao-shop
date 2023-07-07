package com.example.kakaoshop.cart.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CartItemResponse {

    private int id;
    private ProductOptionResponse option;
    private int quantity;
    private int price;

}
