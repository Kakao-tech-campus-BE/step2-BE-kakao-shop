package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRespDTO {
    private int id;
    private List<ProductDTO> productDTOList;
    private int totalPrice;

    @Builder
    public OrderRespDTO(int id, List<ProductDTO> productDTOList, int totalPrice){
        this.id = id;
        this.productDTOList = productDTOList;
        this.totalPrice = totalPrice;
    }

}
