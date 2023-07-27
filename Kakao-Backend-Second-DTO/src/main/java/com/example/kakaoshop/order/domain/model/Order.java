package com.example.kakaoshop.order.domain.model;

import com.example.kakaoshop.user.User;
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
