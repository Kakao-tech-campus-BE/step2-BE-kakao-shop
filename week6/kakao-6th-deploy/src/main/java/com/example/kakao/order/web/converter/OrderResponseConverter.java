package com.example.kakao.order.web.converter;

import com.example.kakao.order.entity.OrderEntity;
import com.example.kakao.order.entity.OrderItemEntity;
import com.example.kakao.order.web.response.OrderResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public final class OrderResponseConverter {
    public static OrderResponse from(OrderEntity orderEntity, List<OrderItemEntity> orderItemEntities, int totalPrice) {
        return OrderResponse.builder()
                .id(orderEntity.getId())
                .products(orderItemEntities.stream()
                        .collect(Collectors.groupingBy(orderItemEntity->orderItemEntity.getOption().getProduct().getProductName()))
                        .entrySet()
                        .stream()
                        .map(OrderProductResponseConverter::from)
                        .collect(Collectors.toList())
                )
                .totalPrice(totalPrice)
                .build();
    }
}

