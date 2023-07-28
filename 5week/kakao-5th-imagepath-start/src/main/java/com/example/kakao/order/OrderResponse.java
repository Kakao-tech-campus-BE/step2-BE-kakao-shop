package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderResponse {


    @Getter
    @Setter
    public static class saveDTO {
        private int orderId;
        private List<ProductDTO> products;
        private int totalPrice;

        public saveDTO(int orderId, List<ProductDTO> products) {
            this.orderId = orderId;
            this.products = products;
            this.totalPrice = products.stream().map(ProductDTO::getItems)
                    .flatMap(List<ItemDTO>::stream)
                    .mapToInt(ItemDTO::getPrice).sum();
        }

        @Getter
        @Setter
        public static class ProductDTO {
            String productName;
            List<ItemDTO> items;
            //int productTotalPrice;

            public ProductDTO(String productName, List<Item> items) {
                this.productName = productName;
                this.items = items.stream().map(ItemDTO::new).collect(Collectors.toList());
                //this.productTotalPrice = items.stream().mapToInt(Item::getPrice).sum();
            }
        }

        @Getter
        @Setter
        public static class ItemDTO {
            int itemId;
            String optionName;
            int quantity;
            int price;

            public ItemDTO(Item item) {
                this.itemId = item.getId();
                this.optionName = item.getOption().getOptionName();
                this.quantity = item.getQuantity();
                this.price = item.getPrice();
            }
        }
    }

    @Getter
    @Setter
    public static class findByIdDTO {

    }
}
