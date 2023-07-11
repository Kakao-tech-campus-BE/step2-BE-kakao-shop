package com.example.kakaoshop.order.response;

import com.example.kakaoshop.cart.response.CartOptionDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDTO {

    private String productName;
    private List<CartOptionDTO> items;

    @Builder()
    public OrderDTO(String productName, List<CartOptionDTO> items) {

        this.productName = productName;
        this.items = items;

    }
}