package com.example.kakao.order.web.response;

import com.example.kakaoshop.order.web.response.OrderProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderResponse {
    private Long orderId;
    private List<OrderProductResponse> products;
    private int totalPrice;

}
