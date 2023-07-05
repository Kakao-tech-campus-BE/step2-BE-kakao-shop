package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductOptionItemDTO {
    private String productName;
    private List<OrderItemDTO> orderItemDTOList;

    @Builder
    public ProductOptionItemDTO(String productName, List<OrderItemDTO> orderItemDTOList){
        this.productName = productName;
        this.orderItemDTOList = orderItemDTOList;
    }
}
