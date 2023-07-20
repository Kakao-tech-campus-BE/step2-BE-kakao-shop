package com.example.kakao.cart;

import com.example.kakao.product.option.ProductOption;
import com.example.kakao.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class CartResponse {


    @Getter @Setter
    public static class UpdateDTO{
        private List<CartDTO> carts;
        private int totalPrice;

        public UpdateDTO(List<Cart> cartList) {
            this.carts = cartList.stream().map(CartDTO::new).collect(Collectors.toList());
            this.totalPrice = cartList.stream().mapToInt(cart -> cart.getProductOption().getPrice() * cart.getQuantity()).sum();
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
                this.optionId = cart.getProductOption().getId();
                this.optionName = cart.getProductOption().getOptionName();
                this.quantity = cart.getQuantity();
                this.price = cart.getProductOption().getPrice() * cart.getQuantity();
            }
        }
    }


    @Getter @Setter
    public static class FindAllDTO{
        private List<ProductDTO> products;
        private int totalPrice;

        public FindAllDTO(List<Cart> cartList) {
            this.products = cartList.stream()
                    .map(cart -> cart.getProductOption().getProduct()).distinct() // 여러 옵션의 상품이 동일하면 중복을 제거한다.
                    .map(product -> new ProductDTO(cartList, product)).collect(Collectors.toList()); // 중복이 제거된 상품과 장바구니 상품으로 DTO를 만든다.
            this.totalPrice = cartList.stream().mapToInt(cart -> cart.getProductOption().getPrice() * cart.getQuantity()).sum();
        }


        @Getter
        @Setter
        public class ProductDTO {
            private int id;
            private String productName;
            private List<CartDTO> carts;

            public ProductDTO(List<Cart> cartList, Product product) {
                this.id = product.getId();
                this.productName = product.getProductName();
                this.carts = cartList.stream()
                        .filter(cart -> cart.getProductOption().getProduct().getId() == product.getId())
                        .map(CartDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter @Setter
            public class CartDTO {
                private int id;
                // Option -> ProductOption 변경
                private ProductOptionDTO option;
                private int quantity;
                private int price;

                public CartDTO(Cart cart) {
                    this.id = cart.getId();
                    // Option -> ProductOption 변경
                    this.option = new ProductOptionDTO(cart.getProductOption());
                    this.quantity = cart.getQuantity();
                    this.price = cart.getProductOption().getPrice()*cart.getQuantity();
                }

                // Option -> ProductOption 변경
                @Getter @Setter
                public class ProductOptionDTO {
                    private int id;
                    private String optionName;
                    private int price;


                    // Option -> ProductOption 변경
                    public ProductOptionDTO(ProductOption productOption) {
                        this.id = productOption.getId();
                        this.optionName = productOption.getOptionName();
                        this.price = productOption.getPrice();
                    }
                }
            }
        }
    }
}
