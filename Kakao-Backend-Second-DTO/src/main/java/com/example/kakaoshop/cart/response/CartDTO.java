package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartDTO {
    private List<CartUpdateResDTO> carts;
    private int totalPrice;

    @Builder
    public CartDTO(List<CartUpdateResDTO> carts, int totalPrice){
        this.carts = carts;
        this.totalPrice = totalPrice;
    }
}
