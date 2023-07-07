package com.example.kakaoshop.cart.request;

import lombok.Getter;

public class UpdateOptionRequestDTO {
    @Getter
    public static class Request {
        private int cartId;
        private int quantity;
    }
}
