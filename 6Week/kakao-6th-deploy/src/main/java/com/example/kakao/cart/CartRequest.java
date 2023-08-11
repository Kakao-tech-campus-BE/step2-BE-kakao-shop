package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;

public class CartRequest {

    @Getter @Setter @ToString
    public static class SaveDTO {
        @Min(value = 1, message = "옵션 정보가 잘못되었습니다.")
        private int optionId;
        @Min(value = 1, message = "수량이 잘못되었습니다.")
        private int quantity;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {
        @Min(value = 1, message = "장바구니 정보가 잘못되었습니다.")
        private int cartId;
        @Min(value = 1, message = "수량이 잘못되었습니다.")
        private int quantity;
    }
}
