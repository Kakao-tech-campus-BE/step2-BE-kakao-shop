package com.example.kakao.cart;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class CartRequest {

    @Getter @ToString
    public static class SaveDTO {
        private int optionId;
        private int quantity;
    }

    @Getter @ToString
    public static class UpdateDTO {
        private int cartId;
        private int quantity;
    }
}
