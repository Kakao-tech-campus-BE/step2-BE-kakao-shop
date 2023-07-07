package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
public class OrderRespSaveDTO {
    private int id;
    private List<OrderProductDTO> products;
    private int totalPrice;

    @Builder
    public OrderRespSaveDTO(int id, List<OrderProductDTO> products, int totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}