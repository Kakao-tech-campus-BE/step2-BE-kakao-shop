package com.example.kakaoshop.cart.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CartReqAddDTO {
    private int optionId;
    private int quantity;

    @Builder
    public CartReqAddDTO(int optionId, int quantity) {
        this.optionId = optionId;
        this.quantity = quantity;
    }
}
