package com.example.kakaoshop.order.item.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponseDTO {

    private Long id;
    private ProductDTO products;
    private Integer totalPrice;

    @Builder
    public OrderResponseDTO(Long id, ProductDTO products, Integer totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
