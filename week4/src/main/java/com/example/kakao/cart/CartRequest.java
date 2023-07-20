package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

public class CartRequest {

    @Getter @Setter @ToString
    public static class SaveDTO {
        @Min(value = 1, message = "optionId must be greater than or equal to 1")
        private int optionId;

        @Min(value = 1, message = "quantity must be greater than or equal to 1")
        private int quantity;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {

        @Min(value = 1, message = "cartId must be greater than or equal to 1")
        private int cartId;

        @Min(value = 1, message = "quantity must be greater than or equal to 1")
        private int quantity;
    }
}
