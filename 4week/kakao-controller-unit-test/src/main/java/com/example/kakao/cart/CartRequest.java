package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class CartRequest {

    @Getter
    @Setter
    public static class SaveDTO {
        @Positive(message = "id 값은 자연수로 작성해주세요")
        private int optionId;

        @NotEmpty
        @Pattern(regexp = "\\d+", message = "수량은 숫자로 작성해주세요")
        private int quantity;

    }

    @Getter
    @Setter
    public static class UpdateDTO {
        @Positive(message = "id 값은 자연수로 작성해주세요")
        private int cartId;

        @NotEmpty
        @Pattern(regexp = "\\d+", message = "수량은 숫자로 작성해주세요")
        private int quantity;

    }
}
