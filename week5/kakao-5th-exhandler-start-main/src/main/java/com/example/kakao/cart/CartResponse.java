package com.example.kakao.cart;

import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class CartResponse {
    @Getter
    public static class FindAllDTO {
        private List<ProductDTO> products;
        private int totalPrice;

        public FindAllDTO(List<Cart> carts) {
            this.products = carts.stream()
                    .map(cart -> cart.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, carts)).collect(Collectors.toList());
            this.totalPrice = carts.stream()
                    .mapToInt(cart -> cart.getOption().getPrice() * cart.getQuantity())
                    .sum();
        }

        @Getter
        private class ProductDTO {
            private int id;
            private String productName;
            private List<CartDTO> carts;

            public ProductDTO(Product product, List<Cart> carts) {
                this.id = product.getId();
                this.productName = product.getProductName();
                this.carts = carts.stream()
                        .filter(cart -> cart.getOption().getProduct().getId()==product.getId())
                        .map(CartDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            private class CartDTO {
                private int id;
                private OptionDTO option;
                private int quantity;
                private int price;

                public CartDTO(Cart cart) {
                    this.id = cart.getId();
                    this.option = new OptionDTO(cart.getOption());
                    this.quantity = cart.getQuantity();
                    this.price = cart.getOption().getPrice() * cart.getQuantity();
                }

                @Getter
                public class OptionDTO {
                    private int id;
                    private String optionName;
                    private int price;

                    public OptionDTO(Option option) {
                        this.id = option.getId();
                        this.optionName = option.getOptionName();
                        this.price = option.getPrice();
                    }
                }
            }
        }
    }

    @Getter
    public static class UpdateDTO {
        private List<CartDTO> carts;
        private int totalPrice;

        public UpdateDTO(List<Cart> carts) {
            this.carts = carts.stream()
                    .map(CartDTO::new).collect(Collectors.toList());
            this.totalPrice = carts.stream()
                    .mapToInt(cart -> cart.getPrice())
                    .sum();
        }

        @Getter
        private class CartDTO {
            private int cartId;
            private int optionId;
            private String optionName;
            private int quantity;
            private int price;

            public CartDTO(Cart cart) {
                this.cartId = cart.getId();
                this.optionId = cart.getOption().getId();
                this.optionName = cart.getOption().getOptionName();
                this.quantity = cart.getQuantity();
                this.price = cart.getPrice();
            }
        }
    }
}
