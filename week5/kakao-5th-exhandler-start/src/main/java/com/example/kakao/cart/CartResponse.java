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
        //cart에 담겨있는 product부분은 cart 엔티티에서 끌어오는 방식으로 한다. cart와 product는 manytomany이기 때문에 db에서 우리가 원했던 형태처럼 나오지 않고
        //중복되어 있는 데이터들이 포함되어 있기 때문이다. 이는 낭비가 될 것 같다.
        public FindAllDTO(List<Cart> cartList) {
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
