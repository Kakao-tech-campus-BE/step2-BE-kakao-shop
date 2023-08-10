package com.example.kakao.order.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderProductResponse {
    private String productName;
    private List<OrderOption> items;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    public static class OrderOption {
        private Long id;
        private String optionName;
        private int quantity;
        private int price;
    }
}