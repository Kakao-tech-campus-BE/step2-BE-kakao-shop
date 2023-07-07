package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter @Setter
public class OrderResSaveDTO {
    private Long id;
    private List<ProductDTO> products;
    private int totalPrice;


    @Builder
    public OrderResSaveDTO(Long id, List<ProductDTO> products, int totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    @Getter
    @Setter
    public static class ProductDTO {
        private String productName;
        private List<OrderItemDTO> items;

        @Builder
        public ProductDTO(String productName, List<OrderItemDTO> items) {
            this.productName = productName;
            this.items = items;
        }
    }

    @Getter @Setter
    public static class OrderItemDTO {
        private Long id;
        private String optionName;
        private int quantity;
        private int price;

        @Builder
        public OrderItemDTO(Long id, String optionName, int quantity, int price) {
            this.id = id;
            this.optionName = optionName;
            this.quantity = quantity;
            this.price = price;
        }
    }

}
