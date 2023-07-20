package com.example.kakao.cart;

import lombok.*;

public class CartRequest {

    @Getter @Setter @ToString @AllArgsConstructor // AllArgsConstructor를 추가하여, test시에 쉽도록 했음
    public static class SaveDTO {
        private int optionId;
        private int quantity;
    }

    @Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
    public static class UpdateDTO {
        private int cartId;
        private int quantity;
    }
}
