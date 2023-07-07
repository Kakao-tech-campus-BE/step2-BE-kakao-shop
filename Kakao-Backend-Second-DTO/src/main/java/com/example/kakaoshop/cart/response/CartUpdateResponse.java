package com.example.kakaoshop.cart.response;

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
