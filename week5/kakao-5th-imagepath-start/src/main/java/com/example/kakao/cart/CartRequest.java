package com.example.kakao.cart;

import lombok.Getter;
import javax.validation.constraints.Min;

public class CartRequest {

    @Getter
    public static class SaveDTO {
        @Min(value = 1)
        private int optionId;
        @Min(value = 1)
        private int quantity;
    }

    @Getter
    public static class UpdateDTO {
        @Min(value = 1)
        private int cartId;
        @Min(value = 1)
        private int quantity;
    }
}
