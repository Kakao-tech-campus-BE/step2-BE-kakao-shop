package com.example.kakao.cart;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class CartRequest {

    @Getter
    @Setter
    @ToString
    public static class SaveDTO {
        @NonNull
        private int optionId;

        @Min(value = 1, message = "잘못된 수량 요청입니다.")
        @Max(value = 1000, message = "잘못된 수량 요청입니다.")
        @NonNull
        private int quantity;

        /*
        @Min(value = 1, message = "잘못된 가격 요청입니다.")
        @Max(value = 10000000, message = "잘못된 가격 요청입니다.")
        private int price; // price 추가했습니다.
        */
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateDTO {
        @NonNull
        private int cartId;

        @Min(value = 1, message = "잘못된 수량 요청입니다.")
        @Max(value = 1000, message = "잘못된 수량 요청입니다.")
        @NonNull
        private int quantity;

        /*
        @Min(value = 1, message = "잘못된 가격 요청입니다.")
        @Max(value = 10000000, message = "잘못된 가격 요청입니다.")
        private int price; // price 추가했습니다.
         */
    }
}
