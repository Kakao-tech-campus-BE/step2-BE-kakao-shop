package com.example.kakaoshop.order.web.converter;

import com.example.kakaoshop.order.entity.OrderEntity;
import com.example.kakaoshop.order.entity.OrderItemEntity;
import com.example.kakaoshop.order.web.response.OrderResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public final class OrderResponseConverter {
    public static OrderResponse from(OrderEntity orderEntity, List<OrderItemEntity> orderItemEntities) {
        return OrderResponse.builder()
                .orderId(orderEntity.getOrderId())
                .products(orderItemEntities.stream()
                        .collect(Collectors.groupingBy(OrderItemEntity::getProductName))
                        .entrySet()
                        .stream()
                        .map(OrderProductResponseConverter::from)
                        .collect(Collectors.toList())
                )
                .totalPrice(orderEntity.getTotalPrice())
                .build();
    }
}

