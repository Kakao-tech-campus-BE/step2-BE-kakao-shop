package com.example.kakaoshop.cart.request;

import lombok.Getter;


public class AddOptionRequestDTO {
    @Getter
    public static class Request {
        private int id;
        private int quantity;
    }
}
