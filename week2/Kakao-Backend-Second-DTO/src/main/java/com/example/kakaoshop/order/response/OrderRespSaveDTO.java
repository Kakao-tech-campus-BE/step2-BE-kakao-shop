package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderRespSaveDTO {
    private Long id;
    private List<ProductOrderItemDTO> products;
    private int totalPrice;

    @Builder
    public OrderRespSaveDTO(Long id, List<ProductOrderItemDTO> products, int totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
