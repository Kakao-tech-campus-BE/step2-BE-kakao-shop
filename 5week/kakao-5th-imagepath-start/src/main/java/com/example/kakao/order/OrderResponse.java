package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class OrderResponse {
    private int id;
    private List<ProductDTO> products;
    private int totalPrice;

    public OrderResponse(Order order, List<Item> items) {
        this.id = order.getId();
        this.products = items
                .stream() // stream
                .map(item -> item.getOption().getProduct()) // item 객체를 product 객체로 매핑
                .distinct() // 중복 제거
                .map(product -> new ProductDTO(product, items)) // product 객체를 productDTO 객체로 매핑
                .collect(Collectors.toList()); // 리스트로 묶기
        this.totalPrice = items.stream()
                .mapToInt(Item::getPrice).sum(); // Price 만 가져와서 더하기
    }
    @Getter @Setter
    public static class ProductDTO {
        private String productName;
        private List<ItemDTO> items;

        public ProductDTO(Product product, List<Item> items) {
            this.productName = product.getProductName();
            this.items = items.stream()
                    // 현재 아이템과 동일한 상품만 담기
                    .filter(item -> item.getOption().getProduct().getId() == product.getId())
                    // 객체 생성
                    .map(ItemDTO::new)
                    .collect(Collectors.toList());
        }
        @Getter @Setter
        public class ItemDTO {
            private int id;
            private String optionName;
            private int quantity;
            private int price;

            public ItemDTO(Item item) {
                this.id = item.getId();
                this.optionName = item.getOption().getOptionName();
                this.quantity = item.getQuantity();
                this.price = item.getPrice();
            }
        }
    }

}
