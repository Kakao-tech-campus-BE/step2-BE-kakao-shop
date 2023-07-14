package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class UpdateOptionResponseDTO {
    @Getter
    @Builder
    public static class Response {
        private List<Item> carts;
        private int totalPrice;

        public Response(List<Item> carts, int totalPrice) {
            this.carts = carts;
            this.totalPrice = totalPrice;
        }
    }

    @Getter
    @Builder
    public static class Item {
        private int cartId;
        private int optionId;
        private String optionName;
        private int quantity;
        private int price;

        public Item(int cartId, int optionId, String optionName, int quantity, int price) {
            this.cartId = cartId;
            this.optionId = optionId;
            this.optionName = optionName;
            this.quantity = quantity;
            this.price = price;
        }
    }
}
