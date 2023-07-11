package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartRespUpdateDTO {

    private List<CartItemUpdateDTO> cartItemUpdateDTOList;
    private int totalPrice;

    @Builder
    public CartRespUpdateDTO(List<CartItemUpdateDTO> cartItemUpdateDTOList, int totalPrice) {
        this.cartItemUpdateDTOList = cartItemUpdateDTOList;
        this.totalPrice = totalPrice;
    }
}
