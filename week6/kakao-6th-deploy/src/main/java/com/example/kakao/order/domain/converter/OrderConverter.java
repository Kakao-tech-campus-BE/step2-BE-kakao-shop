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
                .orderId(entity.getId())
                .user(entity.getUser())
                .build();
    }

    public static OrderEntity to(Order order) {
        return OrderEntity.builder()
                .id(order.getOrderId())
                .user(order.getUser())
                .build();
    }

    public static OrderEntity to(User user) {
        return OrderEntity.builder()
                .user(user)
                .build();
    }

}
