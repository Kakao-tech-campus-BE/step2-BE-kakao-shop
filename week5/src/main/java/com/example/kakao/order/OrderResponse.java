package com.example.kakao.order;

import com.example.kakao.cart.Cart;
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

        public SaveDTO(List<Cart> cartList) {
            this.id = cartList.get(0).getId();
            this.products = cartList.stream()
                    .map(cart -> cart.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, cartList))
                    .collect(Collectors.toList());
            this.totalPrice = cartList.stream().mapToInt(Cart::getPrice).sum();
        }

        @Getter @Setter
        public class ProductDTO {
            private String productName;
            private List<ItemDTO> items;
            public ProductDTO(Product product, List<Cart> cartList) {
                this.productName = product.getProductName();
                this.items = cartList.stream()
                        .filter(cart -> cart.getOption().getProduct().getId() == product.getId())
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter @Setter
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

    @Getter @Setter
    public static class FindByIdDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public FindByIdDTO(List<Item> itemList) {
            this.id = itemList.get(0).getId();
            this.products = itemList.stream()
                    .map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, itemList))
                    .collect(Collectors.toList());
            this.totalPrice = itemList.stream().mapToInt(Item::getPrice).sum();
        }

        @Getter @Setter
        public class ProductDTO {
            private String productName;
            private List<ItemDTO> items;
            public ProductDTO(Product product, List<Item> itemList) {
                this.productName = product.getProductName();
                this.items = itemList.stream()
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
