package com.example.kakaoshop.cart.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartUpdateResDTO {
    private List<CartItemUpdateResDTO> carts;
    private Long totalPrice;
}
