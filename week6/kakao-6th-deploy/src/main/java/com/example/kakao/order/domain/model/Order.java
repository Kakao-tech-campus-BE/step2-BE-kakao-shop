package com.example.kakao.order.domain.model;

import antlr.collections.impl.LList;
import com.example.kakao.user.User;
import lombok.*;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Order {
    private Long orderId;
    private User user;
    private List<OrderItem> orderItems;

    public void addOrderItems(List<OrderItem> orderItems) {
        orderItems.addAll(List.copyOf(orderItems));
    }
}
