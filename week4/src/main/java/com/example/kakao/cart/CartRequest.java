package com.example.kakao.cart;

import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

public class CartRequest {

    @Getter @Setter @ToString
    public static class SaveDTO {
        @Min(value = 1, message = "optionId는 숫자이고 1이상이어야 합니다.")
        private int optionId;

        @Min(value = 1, message = "quantity는 숫자이고 1이상이어야 합니다.")
        private int quantity;


    }

    @Getter @Setter @ToString
    public static class UpdateDTO {

        @Min(value = 1, message = "cartId는 숫자이고 1이상이어야 합니다.")
        private int cartId;

        @Min(value = 1, message = "quantity는 숫자이고 1이상이어야 합니다.")
        private int quantity;
    }
}
