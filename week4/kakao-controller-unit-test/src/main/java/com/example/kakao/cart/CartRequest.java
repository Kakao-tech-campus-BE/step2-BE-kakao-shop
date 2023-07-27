package com.example.kakao.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class CartRequest {

    @Getter @Setter @ToString @Builder
    public static class SaveDTO {
        private int optionId;
        private int quantity;
    }

    @Getter @Setter @ToString @Builder
    public static class UpdateDTO {
        private int cartId;
        private int quantity;
    }
}
