package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

public class OrderProductDTO {
    @Getter
    public static class SaveDTO {
        private String productName;
        private List<OrderItemDTO.SaveDTO> items;

        @Builder
        public  SaveDTO(String productName, List<OrderItemDTO.SaveDTO> items) {
            this.productName = productName;
            this.items = items;
        }
    }

    @Getter
    public static class ConfirmDTO {
        private String productName;
        private List<OrderItemDTO.ConfirmDTO> items;

        @Builder
        public  ConfirmDTO(String productName, List<OrderItemDTO.ConfirmDTO> items) {
            this.productName = productName;
            this.items = items;
        }
    }
}