package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import com.example.kakao.order.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    @Getter
    @Setter
    public static class FindAllDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public FindAllDTO(int id, List<Cart> cartList, int totalPrice) {
            this.id = id;
            this.products = cartList.stream()
                    .collect(Collectors.groupingBy(cart -> cart.getOption().getProduct().getProductName()))
                    .entrySet().stream()
                    .map(entry -> new ProductDTO(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
            this.totalPrice = totalPrice;
        }

        @Getter
        @Setter
        public class ProductDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(String productName, List<Cart> cartList) {
                this.productName = productName;
                this.items = cartList.stream()
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
        private List<FindByIdDTO.ProductByIdDTO> products;
        private int totalPrice;

        public FindByIdDTO(int id, List<Item> itemList, int totalPrice) {
            this.id = id;
            this.products = itemList.stream()
                    .collect(Collectors.groupingBy(item -> item.getOption().getProduct().getProductName()))
                    .entrySet().stream()
                    .map(entry -> new ProductByIdDTO(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
            this.totalPrice = totalPrice;
        }


        @Getter
        @Setter
        public class ProductByIdDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductByIdDTO(String productName, List<Item> itemList) {
                this.productName = productName;
                this.items = itemList.stream()
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
                    this.price = item.getPrice();
                }
            }
        }
    }

}
