package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {


    @Getter
    public static class FindByIdDTO {
        int id;
        List<ProductDTO> products;
        int totalPrice;

        @Builder
        public FindByIdDTO(List<Item> items, Order order) {
            this.id = order.getId();
            this.products = items.stream()
                    // 중복되는 상품 걸러내기
                    .map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, items)).collect(Collectors.toList());
            this.totalPrice = items.stream().mapToInt(cart -> cart.getOption().getPrice() * cart.getQuantity()).sum();
        }
    }


    @Getter
    @Setter
    public static class SaveDTO {
        int id;
        List<ProductDTO> products;
        int totalPrice;

        @Builder
        public SaveDTO(List<Item> items, Order order, int totalPrice) {
            this.id = order.getId();
            this.products = items.stream()
                    // 중복되는 상품 걸러내기
                    .collect(Collectors.groupingBy(item -> item.getOption().getProduct()))
                    .entrySet()
                    .stream()
                    .map(e -> ProductDTO.builder()
                            .product(e.getKey())
                            .items(e.getValue())
                            .build())
                    .collect(Collectors.toList());

            this.totalPrice = totalPrice;
        }
    }

    @Getter
    @Setter
    public static class ProductDTO {
        private String productName;
        private List<ItemDTO> items;

        @Builder
        public ProductDTO(Product product, List<Item> items) {
            this.productName = product.getProductName();
            // 현재 상품과 동일한 주문 내역만 담기
            this.items = items.stream()
                    .filter(item -> item.getOption().getProduct().getId() == product.getId())
                    .map(ItemDTO::new)
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    public static class ItemDTO {
        int id;
        String optionName;
        int quantity;
        int price;

        @Builder
        public ItemDTO(Item item) {
            this.id = item.getId();
            this.optionName = item.getOption().getOptionName();
            this.quantity = item.getQuantity();
            this.price = item.getPrice();
        }
    }
}
