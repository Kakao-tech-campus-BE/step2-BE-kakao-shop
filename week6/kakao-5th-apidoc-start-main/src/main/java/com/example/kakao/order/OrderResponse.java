package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {
    @Getter
    public static class SaveDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public SaveDTO(Order order, List<Item> items) {
            this.id = order.getId();
            this.products = items.stream()
                    .map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(items, product))
                    .collect(Collectors.toList());
            this.totalPrice = items.stream()
                    .map(x->x.getPrice())
                    .reduce(0, (a,b)->a+b);
        }

        @Getter
        private class ProductDTO {
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(List<Item> items, Product product){
                this.productName = product.getProductName();
                this.items = items.stream()
                        .filter(item -> item.getOption().getProduct().getId() == product.getId())
                        .map(item -> new ItemDTO(item.getOption(), item.getQuantity()))
                        .collect(Collectors.toList());
            }
            @Getter
            private class ItemDTO {
                private int id;
                private String optionName;
                private int quantity;
                private int price;

                public ItemDTO(Option option, int quantity) {
                    this.id = option.getId();
                    this.optionName = option.getOptionName();
                    this.quantity = quantity;
                    this.price = option.getPrice();
                }
            }
        }
    }
}
