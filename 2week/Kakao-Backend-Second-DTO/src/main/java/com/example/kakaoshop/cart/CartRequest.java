package com.example.kakaoshop.cart;

import lombok.*;

public class CartRequest {
    @Getter
    @Setter
    public static class AddDTO{
        private int optionId;
        private int quantity;

        @Builder
        public AddDTO(int optionId, int quantity){
            this.optionId = optionId;
            this.quantity = quantity;
        }
    }

    @Getter
    @Setter
    public static class UpdateDTO{
        private int cartId;
        private int quantity;

        @Builder
        public UpdateDTO(int cartId, int quantity){
            this.cartId = cartId;
            this.quantity = quantity;
        }
    }

}
