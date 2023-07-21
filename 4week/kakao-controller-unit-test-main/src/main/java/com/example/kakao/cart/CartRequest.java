package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

public class CartRequest {

    @Validated
    @Getter @Setter @ToString
    public static class SaveDTO {
        private int optionId;
        @Min(value = 0, message = "Quantity must be greater than 0")
        private int quantity;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {
        private int cartId;
        @Min(value = 0, message = "Quantity must be greater than 0")
        private int quantity;
    }
}
