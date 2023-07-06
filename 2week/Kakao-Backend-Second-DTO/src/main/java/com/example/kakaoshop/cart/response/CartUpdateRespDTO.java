package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartUpdateRespDTO {
    private List<CartItems> carts;
    private int totalPrice;

    @Builder
    public CartUpdateRespDTO(List<CartItems> carts, int totalPrice) {
        this.carts = carts;
        this.totalPrice = totalPrice;
    }
}
