package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class CartReqRespDTO {
    @Data
    public static class CartUpdateRequestDTO {
        private int cartId;
        private int quantity;

        @Builder
        public CartUpdateRequestDTO(int cartId, int quantity) {
            this.cartId = cartId;
            this.quantity = quantity;
        }
    }

    @Data
    public static class CartUpdateResponseDTO {
        private List<CartInfoDTO> carts;
        private int totalPrice;

        @Builder
        public CartUpdateResponseDTO(List<CartInfoDTO> carts, int totalPrice) {
            this.carts = carts;
            this.totalPrice = totalPrice;
        }
    }

    @Data
    public static class CartInfoDTO {
        private int cartId;
        private int optionId;
        private String optionName;
        private int quantity;
        private int price;

        @Builder
        public CartInfoDTO(int cartId, int optionId, String optionName, int quantity, int price) {
            this.cartId = cartId;
            this.optionId = optionId;
            this.optionName = optionName;
            this.quantity = quantity;
            this.price = price;
        }
    }
    @Data
    public static class CartDTO {
        private int cartId;
        private int quantity;

        @Builder
        public CartDTO(int cartId, int quantity) {
            this.cartId = cartId;
            this.quantity = quantity;
        }
    }

}
