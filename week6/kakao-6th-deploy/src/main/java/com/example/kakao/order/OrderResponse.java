package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderResponse {
    @Getter
    public static class SaveDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public SaveDTO(List<Item> items) {
            this.id = items.get(0).getOrder().getId();
            Map<String, List<Item>> itemsByProduct = items.stream()
                    .collect(Collectors.groupingBy(item -> item.getOption().getProduct().getProductName()));

            this.products = itemsByProduct.entrySet().stream()
                    .map(entry -> new ProductDTO(entry.getValue()))
                    .collect(Collectors.toList());

            this.totalPrice = items.stream().mapToInt(Item::getPrice).sum();
        }

        @Getter
        public class ProductDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(List<Item> items) {
                this.productName = items.get(0).getOption().getProduct().getProductName();
                this.items = items.stream()
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
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
    public static class FindDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public FindDTO(List<Item> items) {
            this.id = items.get(0).getOrder().getId();
            Map<String, List<Item>> itemsByProduct = items.stream()
                    .collect(Collectors.groupingBy(item -> item.getOption().getProduct().getProductName()));

            this.products = itemsByProduct.entrySet().stream()
                    .map(entry -> new ProductDTO(entry.getValue()))
                    .collect(Collectors.toList());

            this.totalPrice = items.stream().mapToInt(Item::getPrice).sum();
        }

        @Getter
        public class ProductDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(List<Item> items) {
                this.productName = items.get(0).getOption().getProduct().getProductName();
                this.items = items.stream()
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
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
