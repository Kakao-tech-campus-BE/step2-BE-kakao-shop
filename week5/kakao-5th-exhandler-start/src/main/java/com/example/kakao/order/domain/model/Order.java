package com.example.kakao.order.domain.model;

import lombok.*;
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Order {
    private Long orderId;
    private User user;
    private Long orderDate;
    private boolean isCancel;
    private int totalPrice;
}
