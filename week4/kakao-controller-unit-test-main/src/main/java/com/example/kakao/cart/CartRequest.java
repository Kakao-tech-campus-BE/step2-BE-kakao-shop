package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class CartRequest {

    @Getter
    @Setter
    @ToString
    public static class SaveDTO {

        @NotNull(message = "옵션 아이디는 필수 입력 값입니다.")
        private Integer optionId;

        @Min(value = 1, message = "수량은 최소 1 이상 이어야 합니다.")
        @Max(value = 100, message = "최대 수량은 100개를 넘을 수 없습니다.")
        private Integer quantity;
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateDTO {

        @NotNull(message = "장바구니 아이디는 필수 입력 값입니다.")
        private Integer cartId;

        @Min(value = 1, message = "수량은 최소 1 이상 이어야 합니다.")
        @Max(value = 100, message = "최대 수량은 100개를 넘을 수 없습니다.")
        private Integer quantity;
    }
}
