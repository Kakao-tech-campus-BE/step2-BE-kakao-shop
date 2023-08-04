package com.example.kakao.cart;

import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class CartResponse {
    @Getter
    @Setter
    public static class FindAllDTO {
        private List<ProductDTO> products;
        private int totalPrice;

        public FindAllDTO(List<Cart> cartList) {
            this.products = cartList.stream()
                    // 중복되는 상품 걸러내기
                    .map(cart -> cart.getOption().getProduct()).distinct()  // 중복 제거
                    .map(product -> new ProductDTO(product, cartList)).collect(Collectors.toList());
            this.totalPrice = cartList.stream().mapToInt(cart -> cart.getOption().getPrice() * cart.getQuantity()).sum();
        }


        @Getter
        @Setter
        public class ProductDTO {
            private int id;
            private String productName;
            private List<CartDTO> carts;

            public ProductDTO(Product product, List<Cart> cartList) {
                this.id = product.getId();
                this.productName = product.getProductName();
                // 현재 상품과 동일한 장바구니 내역만 담기
                this.carts = cartList.stream()
                        .filter(cart -> cart.getOption().getProduct().getId() == product.getId())
                        .map(CartDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            @Setter
            public class CartDTO {
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
                @Setter
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
    @Setter
    public static class UpdateDTO {
        private List<CartDTO> carts;
        private int totalPrice;

        public UpdateDTO(List<Cart> cartList) {
            this.carts = cartList.stream().map(CartDTO::new).collect(Collectors.toList());
            this.totalPrice = cartList.stream().mapToInt(cart -> cart.getPrice()).sum();
        }


        @Getter
        @Setter
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
