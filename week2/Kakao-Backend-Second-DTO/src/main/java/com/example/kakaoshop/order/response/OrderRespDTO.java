package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

public class OrderRespDTO {
    @Getter
    public static class SaveDTO {
        private int id;
        private List<OrderProductDTO.SaveDTO> products;
        private int totalPrice;

        @Builder
        public SaveDTO(int id, List<OrderProductDTO.SaveDTO> products, int totalPrice) {
            this.id = id;
            this.products = products;
            this.totalPrice = totalPrice;
        }
    }

    @Getter
    public static class ConfirmDTO {
        private int id;
        private List<OrderProductDTO.ConfirmDTO> products;
        private int totalPrice;

        @Builder
        public ConfirmDTO(int id, List<OrderProductDTO.ConfirmDTO> products, int totalPrice) {
            this.id = id;
            this.products = products;
            this.totalPrice = totalPrice;
        }
    }
}