package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// setter가 필요한가..?
@Getter @Setter
public class OrderRespFindByIdDTO {
    private Long id;
    private List<ProductOrderItemDTO> products;
    private int totalPrice;

    @Builder
    public OrderRespFindByIdDTO(Long id, List<ProductOrderItemDTO> products, int totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
