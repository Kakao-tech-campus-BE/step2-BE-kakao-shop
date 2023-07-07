package com.example.kakaoshop.order.item.response;

import lombok.Builder;
import lombok.Data;

@Data
public class OrderResponseFindByIdDTO {

    private Long id;
    private ProductDTO products;
    private Integer totalPrice;

    @Builder
    public OrderResponseFindByIdDTO(Long id, ProductDTO products, Integer totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
