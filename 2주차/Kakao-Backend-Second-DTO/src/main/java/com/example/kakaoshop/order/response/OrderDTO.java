package com.example.kakaoshop.order.response;

import com.example.kakaoshop.order.response.ProductDTO;
import com.example.kakaoshop.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private List<ProductDTO> products;
    private int totalPrice;
    @Builder
    public OrderDTO(List<ProductDTO> products, int totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
