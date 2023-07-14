package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdatedCartDTO {
    final private List<UpdatedCartItemDTO> carts;
    final private int totalPrice;

    @Builder
    public UpdatedCartDTO(List<UpdatedCartItemDTO> carts, int totalPrice) {
        this.carts = carts;
        this.totalPrice = totalPrice;
    }
}
