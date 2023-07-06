package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderProductDTO {
        private String productName;
        private List<OrderItemDTO> items;

        @Builder
        public OrderProductDTO(String productName, List<OrderItemDTO> items) {
                this.productName = productName;
                this.items = items;
        }
}
