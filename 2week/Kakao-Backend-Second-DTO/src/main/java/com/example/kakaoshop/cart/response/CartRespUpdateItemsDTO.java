package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartRespUpdateItemsDTO {

    private List<CartDTO> carts;
    private int totalPrice;

    @Builder
    public CartRespUpdateItemsDTO(List<CartDTO> carts, int totalPrice) {
        this.carts = carts;
        this.totalPrice = totalPrice;
    }

}
