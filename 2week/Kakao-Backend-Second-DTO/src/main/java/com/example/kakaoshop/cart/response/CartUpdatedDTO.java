package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CartUpdatedDTO {

    private List<CartItemDTO> carts;
    private int totalPrice;

    @Builder
    public CartUpdatedDTO(List<CartItemDTO> carts, int totalPrice){
        this.carts = carts;
        this.totalPrice = totalPrice;
    }
}
