package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class CartResponse {
    @Getter
    @Setter
    public static class FindAllDTOv2 {
        private List<ProductDTO> products;

        public FindAllDTOv2(List<Cart> cartList) {
            this.products = cartList.stream().map(cart -> new ProductDTO(cart)).collect(Collectors.toList());
        }

        @Getter
        @Setter
        public class ProductDTO {
            private int productId;
            private String productName;
            private int cartId;
            private String optionName;
            private int quantity;
            private int price;

            public ProductDTO(Cart cart) {
                this.productId = cart.getOption().getProduct().getId(); // product - lazy loading
                this.productName = cart.getOption().getProduct().getProductName();
                this.cartId = cart.getId();
                this.optionName = cart.getOption().getOptionName(); // option - lazy loading
                this.quantity = cart.getQuantity();
                this.price = cart.getPrice();
            } // lazy loading 2번 발생
        }
    }

}
