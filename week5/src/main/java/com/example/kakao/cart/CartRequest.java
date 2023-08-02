package com.example.kakao.cart;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class CartRequest {

    @Getter @Setter @ToString
    public static class SaveDTO {
        @NotNull
        private int optionId;
        @NotNull
        private int quantity;
        //private int price;
    }

    //Validation 추가
    @Getter @Setter @ToString
    public static class UpdateDTO {
        @NotNull
        private int cartId;
        @NotNull
        private int quantity;
    }
}
