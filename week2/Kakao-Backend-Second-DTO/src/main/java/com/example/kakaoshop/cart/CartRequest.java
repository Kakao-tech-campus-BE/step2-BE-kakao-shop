package com.example.kakaoshop.cart;

import lombok.Getter;
import lombok.Setter;

public class CartRequest {
    @Getter
    @Setter
    public static class AddDTO {
        private int optionId;
        private int quantity;
    }
    @Getter
    @Setter
    public static class UpdateDTO{
        private int cartId;
        private int quantity;
    }
}
