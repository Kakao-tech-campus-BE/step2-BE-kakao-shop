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
        private List<ProductDTO> products;
        private int totalPrice;

        public SaveDTO(List<Item> orderItems) {
            this.id = orderItems.get(0).getOrder().getId();
            this.products = orderItems.stream()
                    .map(item -> item.getOption().getProduct())
                    .distinct()
                    .map(product -> new ProductDTO(product, orderItems))
                    .collect(Collectors.toList());
            this.totalPrice = orderItems.stream()
                    .mapToInt(Item::getPrice)
                    .sum();
        }

        @Getter @Setter
        private static class ProductDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(Product product, List<Item> orderItems) {
                this.productName = product.getProductName();
                this.items = orderItems.stream()
                        .filter(item -> item.getOption().getProduct().getId() == product.getId())
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter @Setter
            private static class ItemDTO {
                private int id;
                private String optionName;
                private int quantity;
                private int price;

                public ItemDTO(Item orderItem) {
                    this.id = orderItem.getId();
                    this.optionName = orderItem.getOption().getOptionName();
                    this.quantity = orderItem.getQuantity();
                    this.price = orderItem.getPrice();
                }
            }
        }
    }

}
