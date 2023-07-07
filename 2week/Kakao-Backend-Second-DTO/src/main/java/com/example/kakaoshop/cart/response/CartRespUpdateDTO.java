package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// 장바구니 수량 수정 시 사용되는 dto
@Getter @Setter
public class CartRespUpdateDTO {
    private List<CartItemResUpdateDTO> cartItemList;
    private int totalPrice;

    @Builder
    public CartRespUpdateDTO(List<CartItemResUpdateDTO> cartItemList, int totalPrice) {
        this.cartItemList = cartItemList;
        this.totalPrice = totalPrice;
    }
}
