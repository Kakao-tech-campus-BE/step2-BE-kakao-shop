package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// 장바구니 수량 수정 시 사용되는 dto
@Getter @Setter
public class CartRespUpdateDTO {
    private List<CartItemUpdateDTO> cartItemList;
    private int totalPrice;

    @Builder
    public CartRespUpdateDTO(List<CartItemUpdateDTO> cartItemList, int totalPrice) {
        this.cartItemList = cartItemList;
        this.totalPrice = totalPrice;
    }
}
