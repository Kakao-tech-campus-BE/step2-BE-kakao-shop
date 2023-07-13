package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartRequest {
        public static class UpdateDTO {
        private int cartId;
        private int quantity;
    }
}
