package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;

public class OrderItem {
    @Getter
    public static class SaveDTO {
        private int id;
        private String optionName;
        private int quantity;
        private int price;

        @Builder
        public SaveDTO(int id, String optionName, int quantity, int price) {
            this.id = id;
            this.optionName = optionName;
            this.quantity = quantity;
            this.price = price;
        }
    }

    @Getter
    public static class ConfirmDTO {
        private int id;
        private String optionName;
        private int quantity;
        private int price;

        @Builder
        public ConfirmDTO(int id, String optionName, int quantity, int price) {
            this.id = id;
            this.optionName = optionName;
            this.quantity = quantity;
            this.price = price;
        }
    }
}
