package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import lombok.Builder;
import lombok.Getter;
import java.util.List;
import java.util.stream.Collectors;


@Getter
public class OrderResponse {
    @Getter
    public static class saveDTO {
        private int id;
        private List<OrderProductDTO> products;
        private int totalPrice;

        @Builder
        public saveDTO(int id, List<Item> itemList) {
            this.id = id;
            this.products = itemList.stream()
                    .collect(Collectors.groupingBy(item -> item.getOption().getProduct()))
                    .entrySet()
                    .stream()
                    .map(e ->OrderProductDTO.builder()
                            .product(e.getKey())
                            .items(e.getValue())
                            .build())
                    .collect(Collectors.toList());

            this.totalPrice = itemList.stream().mapToInt(item -> item.getPrice() * item.getQuantity()).sum();
        }
    }

    @Getter
    public static class OrderProductDTO {
        private String productName;
        private List<ItemDTO> items;
        @Builder
        public OrderProductDTO(Product product, List<Item> items) {
            this.productName = product.getProductName();
            this.items = items.stream()
                    .map(ItemDTO::new)
                    .collect(Collectors.toList());
        }
    }

    @Getter
    public static class ItemDTO {
        private int id;
        private String optionName;
        private int quantity;
        private int price;
        @Builder
        public ItemDTO(Item item) {
            this.id = item.getId();
            this.optionName = item.getOption().getOptionName();
            this.quantity = item.getQuantity();
            this.price = item.getPrice();
        }
    }

}

