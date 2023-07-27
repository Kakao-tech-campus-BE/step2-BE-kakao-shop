package com.example.kakao.cart;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CartRequest {

    @Validated
    @Getter @Setter @ToString
    public static class SaveDTO {
        @Min(value = 1, message = "id는 1부터 시작합니다.")
        private int optionId;

        @Min(value = 1, message = "최소 금액은 1원부터 시작합니다.")
        private int quantity;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {
        private int cartId;
        private int quantity;
    }
}
