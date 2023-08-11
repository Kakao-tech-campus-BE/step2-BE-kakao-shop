package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    @Getter @Setter
    public static class FindByIdDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public FindByIdDTO(Order order, List<Item> items) {
            this.id = order.getId();
            this.products = items.stream().map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(items, product))
                    .collect(Collectors.toList());
            this.totalPrice = items.stream().mapToInt(item -> item.getPrice() * item.getQuantity()).sum();
        }

        @Getter @Setter
        public class ProductDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(List<Item> items, Product product) {
                this.productName = product.getProductName();
                this.items = items.stream()
                        .filter(item -> item.getOption().getProduct().getId() == product.getId())
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
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

}
