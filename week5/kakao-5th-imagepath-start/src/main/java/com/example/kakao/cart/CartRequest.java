package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Positive;

public class CartRequest {

    @Getter @Setter @ToString
    public static class SaveDTO {
        // @NotNull // int형의 경우 Data Binding시 null -> 0으로 바뀌어 NotNull이 의미없게된다.
        @Positive
        private int optionId;

        @Positive
        private int quantity;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {
        @Positive
        private int cartId;

        @Positive
        private int quantity;
    }
}
