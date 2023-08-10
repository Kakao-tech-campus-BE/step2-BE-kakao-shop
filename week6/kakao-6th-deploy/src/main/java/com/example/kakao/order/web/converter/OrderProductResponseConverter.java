package com.example.kakao.order.web.converter;

import com.example.kakao.order.entity.OrderItemEntity;
import com.example.kakao.order.web.response.OrderProductResponse;
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
                .items(from(orderEntitiesGroupingProduct.getValue()))
                .build();
    }

    private static List<OrderProductResponse.OrderOption> from(List<OrderItemEntity> orderItemEntities) {
        return orderItemEntities.stream()
                .map(OrderProductResponseConverter::from)
                .collect(Collectors.toList());
    }

    private static OrderProductResponse.OrderOption from(OrderItemEntity orderItemEntity) {
        return OrderProductResponse.OrderOption.builder()
                .id(orderItemEntity.getOption().getId())
                .optionName(orderItemEntity.getOption().getOptionName())
                .quantity(orderItemEntity.getQuantity())
                .price(orderItemEntity.getPrice())
                .build();
    }
}
