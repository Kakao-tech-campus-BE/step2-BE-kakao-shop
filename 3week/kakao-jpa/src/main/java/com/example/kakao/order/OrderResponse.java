package com.example.kakao.order;

import com.example.kakao.order.item.OrderItem;
import com.example.kakao.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    @Getter @Setter
    public static class FindByIdDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public FindByIdDTO(Order order, List<OrderItem> orderItemList) {
            this.id = order.getId();
            this.products = orderItemList.stream()
                    .map(item -> item.getProductOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(orderItemList, product)).collect(Collectors.toList());
            this.totalPrice = orderItemList.stream().mapToInt(item -> item.getProductOption().getPrice() * item.getQuantity()).sum();
        }


        @Getter @Setter
        public class ProductDTO {
            private int id;
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(List<OrderItem> orderItemList, Product product) {
                this.id = product.getId();
                this.productName = product.getProductName();
                this.items = orderItemList.stream()
                        .filter(item -> item.getProductOption().getProduct().getId() == product.getId())
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter @Setter
            public class ItemDTO {
                private int id;
                private String optionName;
                private int quantity;
                private int price;

                public ItemDTO(OrderItem orderItem) {
                    this.id = orderItem.getId();
                    this.optionName = orderItem.getProductOption().getOptionName();
                    this.quantity = orderItem.getQuantity();
                    this.price = orderItem.getProductOption().getPrice()* orderItem.getQuantity();
                }

            }
        }
    }
}
