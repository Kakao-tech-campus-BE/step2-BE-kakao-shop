package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class FindAllResponseDTO {
    @Getter
    @Builder
    public static class Response {
        private List<Product> products;
        private int totalPrice;

        public Response(List<Product> products, int totalPrice) {
            this.products = products;
            this.totalPrice = totalPrice;
        }
    }

    @Getter
    @Builder
    public static class Product{
        private int id;
        private String productName;
        private List<Item> carts;

        public Product(int id, String productName, List<Item> carts) {
            this.id = id;
            this.productName = productName;
            this.carts = carts;
        }
    }
    @Getter
    @Builder
    public static class Item{
        private int id;
        private Option option;
        private int quantity;
        private int price;

        public Item(int id, Option option, int quantity, int price) {
            this.id = id;
            this.option = option;
            this.quantity = quantity;
            this.price = price;
        }
    }

    @Getter
    @Builder
    public static class Option{
        private int id;
        private String optionName;
        private int price;

        public Option(int id, String optionName, int price) {
            this.id = id;
            this.optionName = optionName;
            this.price = price;
        }
    }
}
