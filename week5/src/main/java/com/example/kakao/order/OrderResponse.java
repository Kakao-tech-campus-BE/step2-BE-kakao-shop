package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {


    @Getter@Setter
    public static class FindByIdDTO {
        private int id;// 주문 번호
        private List<ProductDTO> products;
        private int totalPrice;

        public FindByIdDTO(int id, List<Item> itemList){
            this.id = id;
            this.products = itemList.stream()
                    .map(item -> new ProductDTO(item.getOption().getProduct(), item))
                    .collect(Collectors.toList());
            this.totalPrice = products.stream()
                    .mapToInt(product -> product.getItems().stream()
                            .mapToInt(item -> item.getPrice() * item.getQuantity())
                            .sum())
                    .sum();
        }
        @Getter@Setter
        public static class ProductDTO {
            private Product product;
            private List<Item> items;

            public ProductDTO(Product product, Item item){
                this.product = product;
                this.items = List.of(item);
            }
            @Getter@Setter
            public static class ItemDTO {
                private int id;
                private int quantity;
                private int price;
                private String optionName;

                public ItemDTO(Item item){
                    this.id = item.getId();
                    this.quantity = item.getQuantity();
                    this.price = item.getPrice();
                    this.optionName = item.getOption().getOptionName();
                }
            }
        }

    }

    public class SaveDTO {
    }
}
