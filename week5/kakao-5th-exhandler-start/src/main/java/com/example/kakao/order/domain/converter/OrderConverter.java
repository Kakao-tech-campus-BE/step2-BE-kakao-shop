package com.example.kakao.order.domain.converter;

import com.example.kakao.order.domain.model.Order;
import com.example.kakao.order.entity.OrderEntity;
import com.example.kakao.user.User;
import lombok.experimental.UtilityClass;
@UtilityClass
public class OrderConverter {
    public static Order from(OrderEntity entity) {
        if (entity == null) {
            return null;
        }

        return Order.builder()
                .orderId(entity.getOrderId())
                .user(entity.getUser())
                .orderDate(entity.getOrderDate())
                .isCancel(entity.isCancel())
                .totalPrice(entity.getTotalPrice())
                .build();
    }

    public static OrderEntity to(Order order) {
        return OrderEntity.builder()
                .orderId(order.getOrderId())
                .user(order.getUser())
                .orderDate(order.getOrderDate())
                .isCancel(order.isCancel())
                .totalPrice(order.getTotalPrice())
                .build();
    }

    public static OrderEntity to(User user, int totalPrice) {
        return OrderEntity.builder()
                .user(user)
                .orderDate(System.currentTimeMillis())
                .isCancel(false)
                .totalPrice(totalPrice)
                .build();
    }

}
