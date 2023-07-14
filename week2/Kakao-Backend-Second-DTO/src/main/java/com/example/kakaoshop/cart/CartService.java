package com.example.kakaoshop.cart;

import lombok.Getter;
import lombok.Setter;

public class CartService {
    @Getter @Setter
    public static class add {
        private int optionId;
        private int quantity;
    }

    @Getter @Setter
    public static class update {
        private int cartId;
        private int quantity;
    }
}