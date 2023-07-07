package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderProductDTO {

    private String productName;
    private List<items> items;

    @Builder
    public OrderProductDTO(int id, String productName, List<items> items) {
        this.productName = productName;
        this.items = items;
    }
}
