package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CartRequest {

    @Getter @Setter @ToString
    public static class SaveDTO {
        @NotNull
        private Integer optionId;

        @Positive
        private int quantity;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {
        @NotNull
        private Integer cartId;

        @Positive
        private int quantity;
    }
}
