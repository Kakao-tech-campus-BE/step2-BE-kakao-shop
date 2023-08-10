package com.example.kakao.cart.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CartSingleOptionResponse {

    private Long id;
    private CartOptionInfoResponse option;
    private int quantity;
    private int price;

}
