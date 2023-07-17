package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class CartRequest {

    @Getter
    @Setter
    @ToString
    public static class SaveDTO {
        @NotEmpty(message = "옵션 아이디는 필수 입력 값입니다.")
        private int optionId;

        @Min(value = 1, message = "수량은 최소 1 이상 이어야 합니다.")
        @Max(value = 100, message = "최대 수량은 100개를 넘을 수 없습니다.")
        private int quantity;
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateDTO {
        @NotEmpty(message = "장바구니 아이디는 필수 입력 값입니다.")
        private int cartId;

        @Min(value = 1, message = "수량은 최소 1 이상 이어야 합니다.")
        @Max(value = 100, message = "최대 수량은 100개를 넘을 수 없습니다.")
        private int quantity;
    }
}
