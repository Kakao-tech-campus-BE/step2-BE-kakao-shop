package com.example.kakao.cart;

import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class CartResponse {

    @Getter
    public static class FindAllDTO {
        private final List<ProductDTO> products;
        private final int totalPrice;

        public FindAllDTO(List<Cart> cartList) {
            this.products = cartList.stream()
                    .map(cart -> cart.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, cartList)).collect(Collectors.toList());
            this.totalPrice = cartList.stream().mapToInt(cart -> cart.getOption().getPrice() * cart.getQuantity()).sum();
        }

        @Getter
        public class ProductDTO {
            private final int id;
            private final String productName;
            private final List<CartDTO> carts;

            public ProductDTO(Product product, List<Cart> cartList) {
                this.id = product.getId();
                this.productName = product.getProductName();
                this.carts = cartList.stream()
                        .filter(cart -> cart.getOption().getProduct().getId() == product.getId())
                        .map(CartDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            public class CartDTO {
                private final int id;
                private final OptionDTO option;
                private final int quantity;
                private final int price;

                public CartDTO(Cart cart) {
                    this.id = cart.getId();
                    this.option = new OptionDTO(cart.getOption());
                    this.quantity = cart.getQuantity();
                    this.price = cart.getOption().getPrice() * cart.getQuantity();
                }

                @Getter
                public class OptionDTO {
                    private final int id;
                    private final String optionName;
                    private final int price;

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
    public static class FindAllDTOv2 {
        private final List<ProductDTO> products;

        public FindAllDTOv2(List<Cart> cartList) {
            this.products = cartList.stream().map(ProductDTO::new).collect(Collectors.toList());
        }

        @Getter
        public class ProductDTO {
            private final int productId;
            private final String productName;
            private final int cartId;
            private final String optionName;
            private final int quantity;
            private final int price;

            public ProductDTO(Cart cart) {
                this.productId = cart.getOption().getProduct().getId();
                this.productName = cart.getOption().getProduct().getProductName();
                this.cartId = cart.getId();
                this.optionName = cart.getOption().getOptionName();
                this.quantity = cart.getQuantity();
                this.price = cart.getPrice();
            }
        }
    }

    @Getter
    public static class UpdateDTO {
        private final List<CartDTO> carts;
        private final int totalPrice;

        public UpdateDTO(List<Cart> cartList) {
            this.carts = cartList.stream().map(CartDTO::new).collect(Collectors.toList());
            this.totalPrice = cartList.stream().mapToInt(cart -> cart.getPrice()).sum();
        }


        @Getter
        public class CartDTO {
            private final int cartId;
            private final int optionId;
            private final String optionName;
            private final int quantity;
            private final int price;

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
