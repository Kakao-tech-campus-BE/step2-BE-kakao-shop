package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

public class CartRequest {

    @Getter @Setter @ToString
    public static class SaveDTO {
        private int optionId;
        private int quantity;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {
        @NotNull(message = "cartId는 null이 될 수 없습니다")
        private int cartId;
        @NotNull(message = "quantity는 null이 될 수 없습니다")
        private int quantity;
    }
}
