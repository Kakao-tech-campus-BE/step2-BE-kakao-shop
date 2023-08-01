package com.example.kakao.order;

import com.example.kakao.product.Product;
import com.example.kakao.order.item.Item;
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

        public SaveDTO(Order order, List<Item> itemList) {
            this.id = order.getId();
            this.products = itemList.stream()
                    .map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(itemList, product)).collect(Collectors.toList());
            this.totalPrice = itemList.stream().mapToInt(item -> item.getOption().getPrice() * item.getQuantity()).sum();
        }

        @Getter
        @Setter
        public class ProductDTO {
            private int id;
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(List<Item> itemList, Product product) {
                this.id = product.getId();
                this.productName = product.getProductName();
                this.items = itemList.stream()
                        .filter(item -> item.getOption().getProduct().getId() == product.getId())
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
