package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    @Getter
    @Setter
    public static class SaveDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public SaveDTO(List<Item> itemList) {
            this.id = getId()+1;
            this.products = itemList.stream()
                    // 중복되는 상품 걸러내기
                    .map(item -> item.getOption().getProduct()).distinct()  // 중복 제거
                    .map(product -> new ProductDTO(product, itemList)).collect(Collectors.toList());
            this.totalPrice = itemList.stream().mapToInt(cart -> cart.getOption().getPrice() * cart.getQuantity()).sum();
        }

        @Getter
        @Setter
        public class ProductDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(Product product, List<Item> itemList) {
                this.productName = product.getProductName();
                // 현재 상품과 동일한 장바구니 내역만 담기
                this.items = itemList.stream()
                        .filter(cart -> cart.getOption().getProduct().getId() == product.getId())
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            @Setter
            public class ItemDTO {
                private int id;
                private String optionName;
                private int quantity;
                private int price;

                public ItemDTO(Item item) {
                    this.id = item.getId();
                    this.optionName = item.getOption().getOptionName();
                    this.quantity = item.getQuantity();
                    this.price = item.getOption().getPrice() * item.getQuantity();
                }
            }
        }
    }

    @Getter
    @Setter
    public static class FindByIdDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public FindByIdDTO(List<Item> itemList) {
            this.id = getId()+1;
            this.products = itemList.stream()
                    // 중복되는 상품 걸러내기
                    .map(item -> item.getOption().getProduct()).distinct()  // 중복 제거
                    .map(product -> new ProductDTO(product, itemList)).collect(Collectors.toList());
            this.totalPrice = itemList.stream().mapToInt(cart -> cart.getOption().getPrice() * cart.getQuantity()).sum();
        }

        @Getter
        @Setter
        public class ProductDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(Product product, List<Item> itemList) {
                this.productName = product.getProductName();
                // 현재 상품과 동일한 장바구니 내역만 담기
                this.items = itemList.stream()
                        .filter(cart -> cart.getOption().getProduct().getId() == product.getId())
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            @Setter
            public class ItemDTO {
                private int id;
                private String optionName;
                private int quantity;
                private int price;

                public ItemDTO(Item item) {
                    this.id = item.getId();
                    this.optionName = item.getOption().getOptionName();
                    this.quantity = item.getQuantity();
                    this.price = item.getOption().getPrice() * item.getQuantity();
                }
            }

        }
    }

}
