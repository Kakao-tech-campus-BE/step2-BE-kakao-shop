package com.example.kakaoshop.cart.request;

import lombok.Getter;
import lombok.Setter;

public class CartRequest {

    @Getter @Setter
    public static class AddDTO{
        private String optionId;
        private String quantity;
    }

    @Getter @Setter
    public static class UpdateDTO {
        private int cartId;
        private int quantity;
    }
}
