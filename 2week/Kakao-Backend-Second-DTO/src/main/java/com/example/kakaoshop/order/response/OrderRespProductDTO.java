package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderRespProductDTO {

    private String productName;

    private List<OrderRespItemDTO> items;

    @Builder
    public OrderRespProductDTO(String productName, List<OrderRespItemDTO> items) {
        this.productName = productName;
        this.items = items;
    }

    //    private class item{
//        private int id;
//        private String optionName;
//        private int quantity;
//
//    }
//
//
//    public OrderRespProductDTO(String productName) {
//        this.productName = productName;
//    }
}
