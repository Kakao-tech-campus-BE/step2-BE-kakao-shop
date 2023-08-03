package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import com.example.kakao.order.Order;
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

        public SaveDTO(Order order, List<Cart> orderItemList) {
            this.id = order.getId() + 1;
            this.products = orderItemList.stream()
                    .map(cart -> cart.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, orderItemList)).collect(Collectors.toList());
            this.totalPrice = orderItemList.stream().mapToInt(cart -> cart.getPrice()).sum();
        }

        @Getter
        @Setter
        public class ProductDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(Product product, List<Cart> orderItemList) {
                this.productName = product.getProductName();
                this.items = orderItemList.stream()
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

                public ItemDTO(Cart cart) {
                    this.id = cart.getId();
                    this.optionName = cart.getOption().getOptionName();
                    this.quantity = cart.getQuantity();
                    this.price = cart.getPrice();
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

        public FindByIdDTO(Order order, List<Item> orderItemList) {
            this.id = order.getId();
            this.products = orderItemList.stream()
                    .map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(orderItemList, product)).collect(Collectors.toList());
            this.totalPrice = orderItemList.stream().mapToInt(item -> item.getPrice()).sum();
        }

        @Getter
        @Setter
        public class ProductDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(List<Item> orderItemList, Product product) {
                this.productName = product.getProductName();
                this.items = orderItemList.stream()
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
