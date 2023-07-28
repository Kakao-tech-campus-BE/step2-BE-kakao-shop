package com.example.kakao.cart;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class CartRequest {

    @Getter @Setter @ToString
    public static class SaveDTO {
        private int optionId;
        private int quantity;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {
        private int cartId;
        
        @Min(value = 1, message = "최소 주문 수량은 1개입니다.")
        @Max(value = Integer.MAX_VALUE, message = "최대 주문 수량을 초과하셨습니다.")
        private int quantity;
    }
}
