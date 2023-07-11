package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

public class OrderResponse {
    @Getter
    public static class SaveDTO {
        private int id;
        private List<OrderProduct.SaveDTO> products;
        private int totalPrice;

        @Builder
        public SaveDTO(int id, List<OrderProduct.SaveDTO> products, int totalPrice) {
            this.id = id;
            this.products = products;
            this.totalPrice = totalPrice;
        }
    }

    @Getter
    public static class ConfirmDTO {
        private int id;
        private List<OrderProduct.ConfirmDTO> products;
        private int totalPrice;

        @Builder
        public ConfirmDTO(int id, List<OrderProduct.ConfirmDTO> products, int totalPrice) {
            this.id = id;
            this.products = products;
            this.totalPrice = totalPrice;
        }
    }
}