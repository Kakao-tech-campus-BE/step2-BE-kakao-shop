package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {
    /* 결재하기(주문 인서트) DTO */
    @Getter
    @Setter
    public static class OrderInsertDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public OrderInsertDTO(List<Item> items) {
            this.id = items.get(0).getOrder().getUser().getId();
            this.products = items.stream()
                    .collect(Collectors.groupingBy(item -> item.getOption().getProduct()))
                    .entrySet().stream()
                    .map(entry -> new ProductDTO(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
            this.totalPrice = items.stream()
                    .mapToInt(Item::getPrice)
                    .sum();
        }

        @Getter
        @Setter
        public static class ProductDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(Product product, List<Item> items) {
                this.productName = product.getProductName();
                this.items = items.stream()
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            @Setter
            public static class ItemDTO {
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

    /* 주문 결과 확인 DTO */
    // 현재는 결재하기 DTO와 동일한 형태를 가지지만 유지, 보수 측면에서 따로 작성하였다.
    @Getter
    @Setter
    public static class OrderCheckDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public OrderCheckDTO(List<Item> items) {
            this.id = items.get(0).getOrder().getUser().getId();
            this.products = items.stream()
                    .collect(Collectors.groupingBy(item -> item.getOption().getProduct()))
                    .entrySet().stream()
                    .map(entry -> new ProductDTO(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
            this.totalPrice = items.stream()
                    .mapToInt(Item::getPrice)
                    .sum();
        }

        @Getter
        @Setter
        public static class ProductDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(Product product, List<Item> items) {
                this.productName = product.getProductName();
                this.items = items.stream()
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            @Setter
            public static class ItemDTO {
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
