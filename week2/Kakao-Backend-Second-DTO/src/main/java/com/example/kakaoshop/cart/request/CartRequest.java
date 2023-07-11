package com.example.kakaoshop.cart.request;

import lombok.Getter;

public class CartRequest {
    @Getter
    public static class AddDTO {
        private int optionId;
        private int quantity;
    }

    @Getter
    public static class UpdateDTO {
        private int cartId;
        private int quantity;
    }
}
