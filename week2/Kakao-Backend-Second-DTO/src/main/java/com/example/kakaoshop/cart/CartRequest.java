package com.example.kakaoshop.cart;

import lombok.Getter;
import lombok.Setter;

public class CartRequest {

    @Getter @Setter
    public static class CartAddDTO {
        private Long optionId;
        private int quantity;
    }

    @Getter @Setter
    public static class CartUpdateDTO {
        private Long cartId;
        private int quantity;
    }

}
