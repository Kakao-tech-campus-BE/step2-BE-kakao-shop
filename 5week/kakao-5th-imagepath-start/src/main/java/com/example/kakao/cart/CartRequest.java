package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CartRequest {

    @Getter
    @Setter
    @ToString
    public static class SaveDTO {
        @NotNull
        private int optionId;
        @NotNull
        @Positive //0개를 입력할수는 없다!
        private int quantity;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {
        @NotNull
        private int cartId;
        @NotNull
        @Positive
        private int quantity;
    }
}
