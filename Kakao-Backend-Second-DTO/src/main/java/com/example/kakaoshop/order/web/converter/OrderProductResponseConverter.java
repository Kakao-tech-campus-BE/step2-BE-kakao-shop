package com.example.kakaoshop.order.web.converter;

import com.example.kakaoshop.order.entity.OrderItemEntity;
import com.example.kakaoshop.order.web.response.OrderProductResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public final class OrderProductResponseConverter {
    public static OrderProductResponse from(Map.Entry<String, List<OrderItemEntity>> orderEntitiesGroupingProduct) {
        return OrderProductResponse.builder()
                .productName(orderEntitiesGroupingProduct.getKey())
                .orderOptions(from(orderEntitiesGroupingProduct.getValue()))
                .build();
    }

    private static List<OrderProductResponse.OrderOption> from(List<OrderItemEntity> orderItemEntities) {
        return orderItemEntities.stream()
                .map(OrderProductResponseConverter::from)
                .collect(Collectors.toList());
    }

    private static OrderProductResponse.OrderOption from(OrderItemEntity orderItemEntity) {
        return OrderProductResponse.OrderOption.builder()
                .optionName(orderItemEntity.getOptionName())
                .quantity(orderItemEntity.getQuantity())
                .price(orderItemEntity.getPrice())
                .build();
    }
}
