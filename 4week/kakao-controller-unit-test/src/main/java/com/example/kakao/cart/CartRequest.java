package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class CartRequest {
    // request body가 있는 것에 대한 DTO

    @Getter @Setter @ToString
    public static class SaveDTO {
        private int optionId;
        private int quantity;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {
        private int cartId;
        private int quantity;
    }
}
