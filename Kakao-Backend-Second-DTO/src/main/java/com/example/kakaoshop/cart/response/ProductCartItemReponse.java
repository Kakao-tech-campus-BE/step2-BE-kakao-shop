package com.example.kakaoshop.cart.response;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductCartItemReponse {

    private int id;
    private String productName;
    private List<CartItemResponse> cartItems;

}
