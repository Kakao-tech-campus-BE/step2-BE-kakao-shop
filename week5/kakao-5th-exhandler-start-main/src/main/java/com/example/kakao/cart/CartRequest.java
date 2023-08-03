package com.example.kakao.cart;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;

public class CartRequest {

    @Getter @ToString
    public static class SaveDTO {
        @Min(value = 1, message = "잘못된 옵션입니다.")
        private int optionId;
        @Min(value = 1, message = "잘못된 수량입니다.")
        private int quantity;
    }

    @Getter @ToString
    public static class UpdateDTO {
        @Min(value = 1, message = "잘못된 옵션입니다.")
        private int cartId;
        @Min(value = 1, message = "잘못된 수량입니다.")
        private int quantity;
    }
}
