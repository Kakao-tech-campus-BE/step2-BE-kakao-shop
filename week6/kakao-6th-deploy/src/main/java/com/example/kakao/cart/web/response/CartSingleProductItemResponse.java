package com.example.kakao.cart.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CartSingleProductItemResponse {

    private Long id;
    private String productName;
    private List<CartSingleOptionResponse> carts;

}
