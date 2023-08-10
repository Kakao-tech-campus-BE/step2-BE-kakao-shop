package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    @Getter @Setter
    public static class saveDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        @Builder
        public saveDTO(int id, List<Item> items) {
            this.id = id;
            this.products = items.stream()
                    .collect(Collectors.groupingBy(item -> item.getOption().getProduct()))
                    .entrySet()
                    .stream()
                    .map(e -> ProductDTO.builder()
                            .product(e.getKey())
                            .items(e.getValue())
                            .build())
                    .collect(Collectors.toList());

            this.totalPrice = items.stream().mapToInt(item -> item.getPrice() * item.getQuantity()).sum();
        }
    }

    @Getter @Setter
    public static class ProductDTO {
        private String productName;
        private List<ItemDTO> items;

        @Builder
        public ProductDTO(Product product, List<Item> items) {
            this.productName = product.getProductName();
            this.items = items.stream()
                    .map(ItemDTO::new)
                    .collect(Collectors.toList());
        }
    }

    @Getter @Setter
    public static class ItemDTO {
        private int id;
        private String optionName;
        private int quantity;
        private int price;

        @Builder
        public ItemDTO(Item item) {
            this.id = item.getId();
            this.optionName = item.getOption().getOptionName();
            this.quantity = item.getQuantity();
            this.price = item.getPrice();
        }


    }

    @Getter @Setter
    public static class findAllDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        @Builder
        public findAllDTO(int id, List<Item> items) {
            this.id = id;
            this.products = items.stream()
                    .collect(Collectors.groupingBy(item -> item.getOption().getProduct()))
                    .entrySet()
                    .stream()
                    .map(e -> ProductDTO.builder()
                            .product(e.getKey())
                            .items(e.getValue())
                            .build())
                    .collect(Collectors.toList());

            this.totalPrice = items.stream().mapToInt(item -> item.getPrice() * item.getQuantity()).sum();
        }
    }
}
