package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartsFindAllDTO {
    private List<OptionDTO> carts;
    private int totalPrice;

    @Builder
    public CartsFindAllDTO(List<OptionDTO> carts, int totalPrice) {
        this.carts = carts;
        this.totalPrice = totalPrice;
    }
}
