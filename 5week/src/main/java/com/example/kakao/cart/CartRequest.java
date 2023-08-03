package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.executable.ValidateOnExecution;
import java.util.List;


public class CartRequest {
    @Getter @Setter @ToString
    public static class SaveDTO {

        @NotBlank
        @Pattern(regexp = "^[1-9]\\d*$", message = "숫자로만 이루어져야 합니다.")
        private int optionId;

        @NotBlank
        @Pattern(regexp = "^[1-9]\\d*$", message = "숫자로만 이루어져야 합니다.")
        private int quantity;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {

        @NotBlank
        @Pattern(regexp = "^[1-9]\\d*$", message = "숫자로만 이루어져야 합니다.")
        private int cartId;

        @NotBlank
        @Pattern(regexp = "^[1-9]\\d*$", message = "숫자로만 이루어져야 합니다.")
        private int quantity;
    }
}
