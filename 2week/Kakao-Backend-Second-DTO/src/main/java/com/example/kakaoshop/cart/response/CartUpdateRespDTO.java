package com.example.kakaoshop.cart.response;


import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import java.util.List;

@Getter
@Setter
public class CartUpdateRespDTO {
    private List<CartItemDTO> carts;
    private int totalPrice;

    @Builder
    public CartUpdateRespDTO(List<CartItemDTO> carts, int totalPrice) {
        this.carts = carts;
        this.totalPrice = totalPrice;
    }
}
