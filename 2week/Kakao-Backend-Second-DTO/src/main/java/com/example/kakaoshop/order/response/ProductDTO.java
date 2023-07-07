package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class ProductDTO {

    // 여기서는 product id가 필요 없다?
    private String productName;
    private List<OrderItemDTO> orderItems;

    @Builder
    public ProductDTO(String productName, List<OrderItemDTO> orderItems) {
        this.productName = productName;
        this.orderItems = orderItems;
    }
}
