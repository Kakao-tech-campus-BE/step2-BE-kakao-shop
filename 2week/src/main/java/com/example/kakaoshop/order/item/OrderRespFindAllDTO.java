package com.example.kakaoshop.order.item;

import com.example.kakaoshop.cart.response.ProductDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class OrderRespFindAllDTO {
    int id;
    List<OrderProductDTO> products;
    int totalPrice;

    OrderRespFindAllDTO(int id, List<OrderProductDTO> products, int totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
