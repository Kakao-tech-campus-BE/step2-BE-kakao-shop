package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {
    @Getter
    @Setter
    public static class saveDTO
    {
        private int id;
        private List<OrderResponse.saveDTO.ProductDTO> products;
        private int totalPrice;

        public saveDTO(Order order, List<Item> itemList)
        {
            this.id = order.getId();
            this.products = itemList.stream()
                    .map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, itemList)).collect(Collectors.toList());
            this.totalPrice = itemList.stream().mapToInt(item -> item.getOption().getPrice() * item.getQuantity()).sum();
        }

        @Getter
        @Setter
        public class ProductDTO
        {
            private String productName;
            private List<OrderResponse.saveDTO.ProductDTO.ItemDTO> items;

            public ProductDTO(Product product, List<Item> itemList)
            {
                this.productName = product.getProductName();
                this.items = itemList.stream()
                        .filter(item -> item.getOption().getProduct().getId() == product.getId())
                        .map(item -> new ItemDTO(item, item.getOption()))
                        .collect(Collectors.toList());
            }

            @Getter
            @Setter
            public class ItemDTO
            {
                private int id;
                private String optionName;
                private int quantity;
                private int price;

                public ItemDTO(Item item, Option option) {
                    this.id = item.getId();
                    this.optionName = option.getOptionName();
                    this.quantity = item.getQuantity();
                    this.price = quantity * option.getPrice();
                }
            }
        }
    }

}