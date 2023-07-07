package com.example.kakaoshop.cart.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CartSaveRequest {
    private List<CartReqeust> cartSaveRequest;
}
