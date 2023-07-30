package com.example.kakao.cart;

import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class CartResponse {

    @Getter @Setter
    public static class FindAllDTO {
        private List<ProductDTO> products;
        private int totalPrice;

        public FindAllDTO(List<Cart> cartList) {
            this.products = cartList.stream()
                    .map(cart -> cart.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, cartList))
                    .collect(Collectors.toList());
            this.totalPrice = cartList.stream()
                    .mapToInt(cart -> cart.getOption().getPrice() * cart.getQuantity())
                    .sum();
        }

        @Getter @Setter
        public class ProductDTO {
            private int id;
            private String productName;
            private List<CartDTO> carts;
            public ProductDTO(Product product, List<Cart> cartList) {
                this.id = product.getId();
                this.productName = product.getProductName();
                this.carts = cartList.stream()
                        .filter(cart -> cart.getOption().getProduct().getId() == product.getId())
                        .map(CartDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter @Setter
            public class CartDTO {
                private int id;
                private OptionDTO option;
                private int quantity;
                private int price;

                public CartDTO(Cart cart) {
                    this.id = cart.getId();
                    this.option = new OptionDTO(cart.getOption());
                    this.quantity = cart.getQuantity();
                    this.price = cart.getPrice();
                }

                @Getter @Setter
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

    // API 문서가 아니라 화면 설계서만 보고 필요한 내용만 추가한 경우
    @Getter @Setter
    public static class FindAllDTOv2 {
        private List<ProductDTO> products;
        private int totalPrice;

        public FindAllDTOv2(List<Cart> cartList){
            this.products = cartList.stream().map(cart -> new ProductDTO(cart)).collect(Collectors.toList());
            this.totalPrice = 0;
        }

        @Getter @Setter
        public class ProductDTO {
            private int productId;
            private String productName;
            private int cartId;
            private int optionId;
            private String optionName;
            private int optionPrice;
            private int quantity;
            private int cartOptionPrice;
            public ProductDTO(Cart cart){
                this.productId = cart.getOption().getProduct().getId(); // LazyLoading
                this.productName = cart.getOption().getProduct().getProductName();
                this.cartId = cart.getId();
                this.optionId = cart.getOption().getId(); // LazyLoading
                this.optionName = cart.getOption().getOptionName();
                this.optionPrice = cart.getOption().getPrice();
                this.quantity = cart.getQuantity();
                this.cartOptionPrice = 0;
            }
        }
    }

    @Getter @Setter
    public static class UpdateDTO {
        private List<CartDTO> carts;
        private int totalPrice;

        public UpdateDTO(List<Cart> cartList) {
            this.carts = cartList.stream().map(CartDTO::new).collect(Collectors.toList());
            this.totalPrice = cartList.stream().mapToInt(cart -> cart.getPrice()).sum();
        }

        @Getter @Setter
        public class CartDTO {
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
