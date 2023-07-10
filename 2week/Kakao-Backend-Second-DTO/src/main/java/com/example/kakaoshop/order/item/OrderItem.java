package com.example.kakaoshop.order.item;

import com.example.kakaoshop.order.response.Products;
import lombok.*;

import java.util.List;

@Getter @Setter
public class OrderItem {

    private int id;
    private List<Products> productDTO;
    private int totalPrice;



    @Builder
    public OrderItem(int id, List<Products> productDTO, int totalPrice) {
        this.id = id;
        this.productDTO = productDTO;
        this.totalPrice = totalPrice;
    }
}
