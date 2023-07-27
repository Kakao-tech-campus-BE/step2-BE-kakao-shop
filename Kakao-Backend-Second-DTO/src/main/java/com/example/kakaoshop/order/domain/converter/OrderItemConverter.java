package com.example.kakaoshop.order.domain.converter;

import com.example.kakaoshop.order.domain.model.OrderItem;
import com.example.kakaoshop.order.entity.OrderEntity;
import com.example.kakaoshop.order.entity.OrderItemEntity;
import com.example.kakaoshop.product.entity.ProductOptionEntity;
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
                .productName(productOptionEntity.getProduct().getProductName())
                .optionName(productOptionEntity.getOptionName())
                .productId(productOptionEntity.getProduct().getProductId())
                .optionId(productOptionEntity.getProductOptionId())
                .price(productOptionEntity.getPrice())
                .quantity(quantity)
                .build();
    }
}