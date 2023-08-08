package com.example.kakao.cart;

import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CartResponse {
    /* 장바구니 조회를 위한 DTO */
    @Getter
    @Setter
    public static class FindAllDTO {
        private List<ProductDTO> products;
        private int totalPrice;

        public FindAllDTO(List<Cart> cartList) {
            Map<Product, List<Cart>> productCartMap = cartList.stream()
                    .collect(Collectors.groupingBy(cart -> cart.getOption().getProduct()));
            this.products = new ArrayList<>();
            this.totalPrice = 0;
            for (Map.Entry<Product, List<Cart>> entry : productCartMap.entrySet()) {
                ProductDTO productDTO = new ProductDTO(entry.getValue());
                this.products.add(productDTO);
                for (ProductDTO.CartDTO cartDTO : productDTO.getCarts()) {
                    this.totalPrice += (cartDTO.getPrice());
                }
            }
        }

        @Getter
        @Setter
        public static class ProductDTO {
            private int id;
            private String productName;
            private List<CartDTO> carts;

            public ProductDTO(List<Cart> cartList) {
                this.id = cartList.get(0).getOption().getProduct().getId();
                this.productName = cartList.get(0).getOption().getProduct().getProductName();
                this.carts = cartList.stream()
                        .map(CartDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            @Setter
            public static class CartDTO {
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
                public static class OptionDTO {
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

    /* 주문하기(장바구니 최종 수량 수정)를 위한 DTO */
    @Getter
    @Setter
    public static class UpdateDTO {
        private List<CartDTO> carts;
        private int totalPrice;

        public UpdateDTO(List<Cart> cartList) {
            this.carts = cartList.stream()
                    .map(CartDTO::new)
                    .collect(Collectors.toList());
            this.totalPrice = cartList.stream()
                    .mapToInt(Cart::getPrice)
                    .sum();
        }

        @Getter
        @Setter
        public static class CartDTO {
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
