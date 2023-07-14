package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


public class OrderSaveResponseDTO {
    @Getter
    @Builder
    public static class Response {
            private int id;
            private List<Product> products;
            private int totalPrice;

        public Response( int id, List<Product > products,int totalPrice){
            this.id = id;
            this.products = products;
            this.totalPrice = totalPrice;
        }
    }

    @Getter
    @Builder
    public static class Product {
        private String productName;
        private List<Item> items;

        public Product(String productName, List<Item> items) {
            this.productName = productName;
            this.items = items;
        }
    }

    @Getter
    @Builder
    public static class Item {
        private int id;
        private String optionName;
        private int quantity;
        private int price;

        public Item(int id, String optionName, int quantity, int price) {
            this.id = id;
            this.optionName = optionName;
            this.quantity = quantity;
            this.price = price;
        }
    }
}
