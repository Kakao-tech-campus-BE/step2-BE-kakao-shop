package com.example.kakaoshop.cart.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CartFindAllResponse {

    private List<CartSingleProductItemResponse> products;
    private int totalPrice;

}
