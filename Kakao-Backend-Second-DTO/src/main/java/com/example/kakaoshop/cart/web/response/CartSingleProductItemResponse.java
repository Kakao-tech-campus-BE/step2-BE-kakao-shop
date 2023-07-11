package com.example.kakaoshop.cart.web.response;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CartSingleProductItemResponse {

    private int id;
    private String productName;
    private List<CartSingleOptionResponse> cartItems;

}
