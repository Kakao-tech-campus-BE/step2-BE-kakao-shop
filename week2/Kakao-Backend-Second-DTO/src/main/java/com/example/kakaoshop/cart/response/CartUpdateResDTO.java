package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartUpdateResDTO {
    private List<CartUpdateDTO> carts;
    private int totalPrice;

    @Builder
    public CartUpdateResDTO(List<CartUpdateDTO> carts, int totalPrice){
        this.carts = carts;
        this.totalPrice = totalPrice;
    }
}
