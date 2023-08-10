package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {
    @Getter @Setter
    public static class SaveDTO {
        private int id;
        private List<ProductDTO> product;
        private int totalPrice;

        public SaveDTO(Order order, List<Item> itemList) {
            this.id = order.getId();

            this.product = itemList.stream()
                    .map(item -> item.getOption().getProduct())
                    .distinct()
                    .map(product -> new ProductDTO(product, itemList))
                    .collect(Collectors.toList());

            this.totalPrice = itemList.stream()
                    .mapToInt(item -> item.getOption().getPrice() * item.getQuantity())
                    .sum();
        }

        @Getter @Setter
        public class ProductDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(Product product, List<Item> itemList) {
                this.productName = product.getProductName();

                this.items = itemList.stream()
                        .filter(item -> item.getOption().getProduct().equals(product))
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }
        }

        @Getter @Setter
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

    @Getter @Setter
    public static class FindByIdDTO {
        private int id;
        private List<ProductDTO> product;
        private int totalPrice;

        public FindByIdDTO(Order order, List<Item> itemList) {
            this.id = order.getId();

            this.product = itemList.stream()
                    .map(item -> item.getOption().getProduct())
                    .distinct()
                    .map(product -> new ProductDTO(product, itemList))
                    .collect(Collectors.toList());

            this.totalPrice = itemList.stream()
                    .mapToInt(item -> item.getOption().getPrice() * item.getQuantity())
                    .sum();
        }

        @Getter @Setter
        public class ProductDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(Product product, List<Item> itemList) {
                this.productName = product.getProductName();
                this.items = itemList.stream()
                        .filter(item -> item.getOption().getProduct().equals(product))
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }
        }

        @Getter @Setter
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