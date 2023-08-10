package com.example.kakao.order.domain.model;

import lombok.*;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderItem {
    private Long orderItemId;
    private Order order;
    private Long optionId;
    private Long productId;
    private String optionName;
    private String productName;
    private int price;
    private int quantity;
}
