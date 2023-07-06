package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdatedCartDto {
    private List<UpdatedItemDTO> carts;
    private int totalPrice;

    @Builder
    public UpdatedCartDto(List<UpdatedItemDTO> carts, int totalPrice) {
        this.carts = carts;
        this.totalPrice = totalPrice;
    }
}
