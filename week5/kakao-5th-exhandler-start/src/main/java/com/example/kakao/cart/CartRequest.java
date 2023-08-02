package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

public class CartRequest {

    @Getter @Setter @ToString
    public static class SaveDTO {
        private int optionId;
        @Positive
        private Integer quantity;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {
        private int cartId;
        @Positive
        private int quantity;
    }
}
