package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {
    @Getter
    @Setter
    public static class saveDTO {
        private int orderId;
        private List<OrderResponse.saveDTO.ItemDTO> items;
        private int totalPrice;

        public saveDTO(List<Item> itemList) {
            this.items = itemList.stream().map(OrderResponse.saveDTO.ItemDTO::new).collect(Collectors.toList());
            this.totalPrice = itemList.stream().mapToInt(item -> item.getPrice()).sum();
        }

        @Getter
        @Setter
        public class ItemDTO {
            private int itemId;
            private String optionName;
            private int quantity;
            private int price;

            public ItemDTO(Item item) {
                this.itemId = item.getId();
                this.optionName = item.getOption().getOptionName();
                this.quantity = item.getQuantity();
                this.price = item.getPrice();
            }
        }
    }

}
