package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import com.example.kakao.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    @Getter
    @Setter
    public static class SaveDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public SaveDTO(Order order, List<Cart> cartList) {
            this.id = order.getId();
            this.products = cartList.stream()
                    // 중복되는 상품 걸러내기
                    .map(cart -> cart.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, cartList)).collect(Collectors.toList());
            this.totalPrice = cartList.stream().mapToInt(cart -> cart.getOption().getPrice() * cart.getQuantity()).sum();
        }

        @Getter
        @Setter
        public class ProductDTO{
            String productName;
            List<ItemDTO> items;

            public ProductDTO(Product product, List<Cart> cartList) {
                this.productName = product.getProductName();
                this.items = cartList.stream()
                        .filter(cart -> cart.getOption().getProduct().getId() == product.getId())
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter
            @Setter
            public class ItemDTO {
                int id;
                String optionName;
                int quantity;
                int price;

                public ItemDTO(Cart cart) {
                    this.id = cart.getId();
                    this.optionName = cart.getOption().getOptionName();
                    this.quantity = cart.getQuantity();
                    this.price = cart.getPrice();
                }
            }
        }

    }

}
