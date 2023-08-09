package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartRequest {

    @Getter
    @Setter
    public static class SaveDTO {

        @NotNull(message = "옵션 아이디는 필수 입력 값입니다.")
        private Integer optionId;

        @NotNull(message = "수량은 필수 입력 값입니다.")
        @Min(value = 1, message = "수량은 최소 1 이상 이어야 합니다.")
        @Max(value = 100, message = "최대 수량은 100개를 넘을 수 없습니다.")
        private Integer quantity;
    }

    @Getter
    @Setter
    public static class UpdateDTO {

        @NotNull(message = "장바구니 아이디는 필수 입력 값입니다.")
        private Integer cartId;

        @NotNull(message = "수량은 필수 입력 값입니다.")
        @Min(value = 1, message = "수량은 최소 1 이상 이어야 합니다.")
        @Max(value = 100, message = "최대 수량은 100개를 넘을 수 없습니다.")
        private Integer quantity;
    }
}