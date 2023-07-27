package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

public class CartRequest {

    @Getter @Setter @ToString
    public static class SaveDTO {
        @NotNull
        private Integer optionId;
        @NotNull
        private Integer quantity;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {
        @NotNull
        private int cartId;
        @NotNull
        private int quantity;
    }
}
