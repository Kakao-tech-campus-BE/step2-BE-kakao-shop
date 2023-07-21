package com.example.kakao.order;

import com.example.kakao.order.item.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderRequest {

    @Getter
    @Setter
    public static class CreateDTO {
        private int userId;
        private List<OrderItemDTO> orderItems;

        public List<OrderItem> toOrderItemList(Order order) {
            return orderItems.stream()
                    .map(dto -> dto.toOrderItem(order))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    public static class OrderItemDTO {
        private int productOptionId;
        private int quantity;
        private int price;

        public OrderItem toOrderItem(Order order) {
            return OrderItem.builder()
                    .id(productOptionId)
                    .quantity(quantity)
                    .price(price)
                    .order(order)
                    .build();
        }
    }
}
