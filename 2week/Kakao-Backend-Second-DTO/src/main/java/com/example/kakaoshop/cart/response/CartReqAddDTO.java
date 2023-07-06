package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;

@Getter // 장바구니 담기시에 사용되는 dto
public class CartReqAddDTO {
    private int optionId;
    private int quantity;

    @Builder
    public CartReqAddDTO(int optionId, int quantity) {
        this.optionId = optionId;
        this.quantity = quantity;
    }
}
