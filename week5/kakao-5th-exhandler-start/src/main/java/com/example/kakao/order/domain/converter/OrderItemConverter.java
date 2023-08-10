package com.example.kakao.order.domain.converter;

import com.example.kakao.order.domain.model.OrderItem;
import com.example.kakao.order.entity.OrderEntity;
import com.example.kakao.order.entity.OrderItemEntity;
import com.example.kakao.product.entity.ProductOptionEntity;
import lombok.experimental.UtilityClass;
@UtilityClass
public class OrderItemConverter {
    public static OrderItem from(OrderItemEntity entity) {
        if (entity == null) {
            return null;
        }

        return OrderItem.builder()
                .orderItemId(entity.getOrderItemId())
                .optionId(entity.getOptionId())
                .productId(entity.getProductId())
                .optionName(entity.getOptionName())
                .productName(entity.getProductName())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .build();
    }

    public static OrderItemEntity to(OrderItem orderItem, OrderEntity orderEntity) {
        return OrderItemEntity.builder()
                .orderItemId(orderItem.getOrderItemId())
                .orderEntity(orderEntity)
                .optionId(orderItem.getOptionId())
                .productId(orderItem.getProductId())
                .optionName(orderItem.getOptionName())
                .productName(orderItem.getProductName())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }

    public static OrderItemEntity to(ProductOptionEntity productOptionEntity, int quantity) {
        return OrderItemEntity.builder()
                .productName(productOptionEntity.getProductEntity().getProductName())
                .optionName(productOptionEntity.getOptionName())
                .productId(productOptionEntity.getProductEntity().getProductId())
                .optionId(productOptionEntity.getProductOptionId())
                .price(productOptionEntity.getPrice())
                .quantity(quantity)
                .build();
    }
}