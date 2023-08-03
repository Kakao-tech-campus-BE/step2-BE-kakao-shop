package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    @Getter
    public static class CreateDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public CreateDTO(Order order, List<Item> itemList) {
            this.id = order.getId();
            this.products = null;
            this.products = itemList.stream()
                    .map(i -> i.getOption().getProduct()).distinct()
                    .map(p -> new ProductDTO(p, itemList))
                    .collect(Collectors.toList());
            this.totalPrice = itemList.stream()
                    .mapToInt(item -> item.getQuantity() * item.getOption().getPrice()).sum();
        }

        @Getter
        public class ProductDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(Product product, List<Item> itemList) {
                this.productName = product.getProductName();
                this.items = null;
                this.items = itemList.stream()
                        .filter(item -> item.getOption().getProduct().getId() == product.getId())
                        .map(ItemDTO::new).collect(Collectors.toList());
            }

            @Getter
            public class ItemDTO {
                private int id;
                private String optionName;
                private int quantity;
                private int price;

                public ItemDTO(Item item) {
                    this.id = item.getId();
                    this.optionName = item.getOption().getOptionName();
                    this.quantity = item.getQuantity();
                    this.price = item.getPrice();
                }
            }
        }
    }

    @Getter
    public static class FindByIdDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public FindByIdDTO(Order order, List<Item> itemList) {
            this.id = order.getId();
            this.products = itemList.stream()
                    .map(i -> i.getOption().getProduct()).distinct()
                    .map(p -> new ProductDTO(p, itemList))
                    .collect(Collectors.toList());
            this.totalPrice = itemList.stream()
                    .mapToInt(item -> item.getQuantity() * item.getOption().getPrice()).sum();
        }

        @Getter
        public class ProductDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(Product product, List<Item> itemList) {
                this.productName = product.getProductName();
                this.items = itemList.stream()
                        .filter(item -> item.getOption().getProduct().getId() == product.getId())
                        .map(ItemDTO::new).collect(Collectors.toList());
            }

            @Getter
            public class ItemDTO {
                private int id;
                private String optionName;
                private int quantity;
                private int price;

                public ItemDTO(Item item) {
                    this.id = item.getId();
                    this.optionName = item.getOption().getOptionName();
                    this.quantity = item.getQuantity();
                    this.price = item.getPrice();
                }
            }
        }
    }
}
