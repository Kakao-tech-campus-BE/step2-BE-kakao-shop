package com.example.kakaoshop.cart;

import lombok.Getter;
import lombok.Setter;

public class CartRequest {
    @Getter
    @Setter
    public static class CartAddDTO {
        private int optionId;
        private int quantity;
    }
}
