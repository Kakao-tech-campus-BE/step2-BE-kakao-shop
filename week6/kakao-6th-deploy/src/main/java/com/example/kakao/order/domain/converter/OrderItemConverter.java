package com.example.kakao.order.domain.converter;

import com.example.kakao.order.domain.model.OrderItem;
import com.example.kakao.order.entity.OrderEntity;
import com.example.kakao.order.entity.OrderItemEntity;
import com.example.kakao.product.domain.converter.ProductOptionConverter;
import com.example.kakao.product.entity.ProductOptionEntity;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class OrderItemConverter {
    public static OrderItem from(OrderItemEntity entity) {
        if (entity == null) {
            return null;
        }

        return OrderItem.builder()
                .orderItemId(entity.getId())
                .order(
                        OrderConverter.from(entity.getOrder())
                )
                .productOption(ProductOptionConverter.from(entity.getOption()))
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .build();
    }

    public static OrderItemEntity to(OrderItem orderItem, OrderEntity orderEntity) {
        return OrderItemEntity.builder()
                .id(orderItem.getOrderItemId())
                .order(orderEntity)
                .option(ProductOptionConverter.to(orderItem.getProductOption()))
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }

    public static OrderItemEntity to(ProductOptionEntity productOptionEntity, int quantity) {
        return OrderItemEntity.builder()
                .option(productOptionEntity)
                .price(productOptionEntity.getPrice())
                .quantity(quantity)
                .build();
    }
}