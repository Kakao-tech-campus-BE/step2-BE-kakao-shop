package com.example.kakaoshop.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;



public class CartRequest {

    @Getter @Setter
    @ToString
    public static class UpdateDTO {
    //    @Min(1)
    //    @NotNull
        private Long cartId;

    //    @Min(0)
    //    @NotNull
        private int quantity;
    }

    @Getter @Setter
    @ToString
    public static class CreateDTO {
        //    @Min(1)
        //    @NotNull
        private Long optionId;

        //    @Min(0)
        //    @NotNull
        private int quantity;
    }

}
