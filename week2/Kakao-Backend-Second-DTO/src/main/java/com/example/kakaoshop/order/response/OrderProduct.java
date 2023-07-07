package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

public class OrderProduct {
    @Getter
    public static class SaveDTO {
        private String productName;
        private List<OrderItem.SaveDTO> items;

        @Builder
        public  SaveDTO(String productName, List<OrderItem.SaveDTO> items) {
            this.productName = productName;
            this.items = items;
        }
    }

    @Getter
    public static class ConfirmDTO {
        private String productName;
        private List<OrderItem.ConfirmDTO> items;

        @Builder
        public  ConfirmDTO(String productName, List<OrderItem.ConfirmDTO> items) {
            this.productName = productName;
            this.items = items;
        }
    }
}