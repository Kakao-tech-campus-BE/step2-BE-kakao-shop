package com.example.kakaoshop.order;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class OrderDTO {
    //주문 확인, 결제하기에서 공통으로 쓰이는 DTO
    @Data
    public static class OrderRespDTO{
        private int id;
        private List<ProductItemDTO> products;
        private int totalPrice;

        @Builder
        public OrderRespDTO(int id, List<ProductItemDTO> products, int totalPrice) {
            this.id = id;
            this.products = products;
            this.totalPrice = totalPrice;
        }
    }
    @Data
    public static class ProductItemDTO{
        private String productName;
        private List<ItemInfoDTO> items;

        @Builder
        public ProductItemDTO(String productName, List<ItemInfoDTO> items) {
            this.productName = productName;
            this.items = items;
        }
    }
    @Data
    public static class ItemInfoDTO{
        private int id;
        private String optionName;
        private int quantity;
        private int price;

        @Builder
        public ItemInfoDTO(int id, String optionName, int quantity, int price) {
            this.id = id;
            this.optionName = optionName;
            this.quantity = quantity;
            this.price = price;
        }
    }
}
