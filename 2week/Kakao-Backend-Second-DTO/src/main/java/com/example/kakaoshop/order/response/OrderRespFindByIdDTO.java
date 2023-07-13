package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRespFindByIdDTO {
    private int order_id;
    private List<ProductDTO> products;
    private int totalPrice;

    @Builder
    public OrderRespFindByIdDTO(int order_id, List<ProductDTO> products, int totalPrice) {
        this.order_id = order_id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
