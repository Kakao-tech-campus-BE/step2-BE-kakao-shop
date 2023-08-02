package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

// OrderResponse.OrderResponse() 만으로 모든 Response가 만들어져야한다. -> 생성자 인자로 엔티티를 받자.

public class OrderResponse {

    @Getter
    @Setter
    public static class FindById {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public FindById(Order order, List<Product> products, List<Item> items) {
            this.id = order.getId();
            this.totalPrice = products.stream().mapToInt(product -> product.getPrice()).sum();
            this.products = products.stream().map(product -> new ProductDTO(product, items)).collect(Collectors.toList());
        }

        @Getter
        @Setter
        public class ProductDTO {
            private String productName;

            private List<ItemDTO> items;

            public ProductDTO(Product product, List<Item> items) {
                this.productName = product.getProductName();
                this.items = items.stream().map(item -> new ItemDTO(item)).collect(Collectors.toList());
            }
        }

        @Getter
        @Setter
        public class ItemDTO {

            private OptionDTO option;

            public ItemDTO(Item item) {
                this.option = new OptionDTO(item);
            }

        }

        @Getter
        @Setter
        public class OptionDTO {
            private int id;
            private String optionName;
            private int quantity;
            private int price;

            public OptionDTO(Item item) {
                this.id = item.getOption().getId();
                this.optionName = item.getOption().getOptionName();
                this.quantity = item.getQuantity();
                this.price = item.getPrice();
            }
        }
    }
}
