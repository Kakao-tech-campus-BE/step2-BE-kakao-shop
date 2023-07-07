// CartUpdateResponseDTO
package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CartUpdateResponseDTO {
    private List<CartItemResponseDTO> carts;
    private int totalPrice;

    @Builder
    public CartUpdateResponseDTO(List<CartItemResponseDTO> carts, int totalPrice) {
        this.carts = carts;
        this.totalPrice = totalPrice;
    }
}
