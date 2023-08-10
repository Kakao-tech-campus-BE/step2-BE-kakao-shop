package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

public class CartRequest {

    @Getter @Setter @ToString
    public static class SaveDTO {
        @NotNull
        private int optionId;
        @NotNull
        private int quantity;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {
        @NotNull
        private int cartId;
        @NotNull
        private int quantity;
    }
}
