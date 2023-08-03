package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CartRequest {

    @Getter @Setter @ToString
    public static class SaveDTO {
        @Positive(message = "0보다 큰 수를 입력해야 합니다.")
        private int optionId;
        @Positive(message = "0보다 큰 수를 입력해야 합니다.")
        private int quantity;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {
        @Positive(message = "0보다 큰 수를 입력해야 합니다.")
        private int cartId;
        @Positive(message = "0보다 큰 수를 입력해야 합니다.")
        private int quantity;
    }
}
