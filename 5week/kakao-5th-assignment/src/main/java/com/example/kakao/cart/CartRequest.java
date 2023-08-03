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

        // price 추가했습니다.
        private int price;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {
        @NonNull
        private int cartId;
        @NonNull
        private int quantity;

        // price 추가했습니다.
        private int price;
    }
}
