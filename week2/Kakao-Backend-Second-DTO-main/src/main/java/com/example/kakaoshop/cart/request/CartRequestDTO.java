package com.example.kakaoshop.cart.request;

import com.example.kakaoshop.cart.response.ProductOptionDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequestDTO {

    private int optionId;
    private int quantity;
    @Builder
    public CartRequestDTO(int optionId, int quantity) {
        this.optionId = optionId;
        this.quantity = quantity;
    }
}
