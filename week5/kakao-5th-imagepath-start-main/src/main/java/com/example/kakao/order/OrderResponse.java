package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    @Getter
    public static class FindByIdDTO{
        private final int id;
        private final List<ProductDTO> products;
        private final int totalPrice;

        public FindByIdDTO(Order order, List<Item> items) {
            this.id = order.getId();
            this.products = items.stream()
                    .map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, items)).collect(Collectors.toList());
            this.totalPrice = items.stream().mapToInt(item -> item.getOption().getPrice() * item.getQuantity()).sum();
        }

        @Getter
        public class ProductDTO{
            private final int id;
            private final String name;
            private final List<ItemDTO> items;

            public ProductDTO(Product product, List<Item> items) {
                this.id = product.getId();
                this.name = product.getProductName();
                this.items = items.stream()
                        .filter(item -> item.getOption().getProduct().getId() == product.getId())
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            public class ItemDTO{
                private final int id;
                private final String optionName;
                private final int quantity;
                private final int price;

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

