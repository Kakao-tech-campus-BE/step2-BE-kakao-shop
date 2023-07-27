package com.example.kakao.cart;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

public class CartRequest {

    @Getter @Setter @ToString
    public static class SaveDTO {
        @NonNull
        private int optionId;
        @NonNull
        private int quantity;
        private int price;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {
        @NonNull
        private int cartId;
        @NonNull
        private int quantity;
        private int price;
    }
}
