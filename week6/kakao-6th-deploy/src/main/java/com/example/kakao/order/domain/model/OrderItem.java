package com.example.kakao.order.domain.model;

import com.example.kakao.product.domain.model.ProductOption;
import lombok.*;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderItem {
    private Long orderItemId;
    private ProductOption productOption;
    private Order order;
    private int price;
    private int quantity;
}
