package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartUpdateResponseDTO {
    private List<CartUpdateItemDTO> carts;
    private int totalPrice;

    @Builder
    public CartUpdateResponseDTO(List<CartUpdateItemDTO> carts, int totalPrice) {
        this.carts = carts;
        this.totalPrice = totalPrice;
    }
}
