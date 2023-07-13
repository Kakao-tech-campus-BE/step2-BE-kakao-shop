package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
//
@Getter
@Setter
public class OrderRespDTO {
    private int id;
    private List<ProductDTO> products = new ArrayList<>();
    private int totalPrice;
@Builder
    public OrderRespDTO(int id, List<ProductDTO> orderItemDTOList, int totalPrice) {
        this.id = id;
        this.products = orderItemDTOList;
        this.totalPrice = totalPrice;
    }
}
