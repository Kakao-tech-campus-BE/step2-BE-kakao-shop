package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class CartRequest {

    @Getter @Setter @ToString
    public static class SaveDTO {
        @Min(value = 1,message = "존재하지 않는 옵션입니다")
        @Max(value = 48,message = "존재하지 않는 옵션입니다")
        private int optionId;
        @Min(value = 1, message = "수량은 1개 이상이어야 합니다")
        private int quantity;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {
        private int cartId;
        @Min(value = 1, message = "수량은 1개 이상이어야 합니다")
        private int quantity;
    }
}
