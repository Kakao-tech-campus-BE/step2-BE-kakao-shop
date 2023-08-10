package com.example.kakao.cart.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CartUpdateResponse {
    private List<CartChangedOptionResponse> cartChangedOptionResponses;
    private int totalPrice;
}
