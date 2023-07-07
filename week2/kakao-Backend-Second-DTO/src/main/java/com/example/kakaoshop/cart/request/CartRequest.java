package com.example.kakaoshop.cart.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CartRequest {

    @Getter
    @Setter
    public static class AddDTO {
        private Long optionId;
        private int quantity;
    }

    @Getter
    @Setter
    public static class updateDTO {
        private Long cartId;
        private int quantity;
    }

}
