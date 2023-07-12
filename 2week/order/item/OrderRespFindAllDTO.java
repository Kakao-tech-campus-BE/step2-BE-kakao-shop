package com.example.kakaoshop.order.item;

import com.example.kakaoshop.order.item.ProductDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;

import java.util.List;

@Getter @Setter
public class OrderRespFindAllDTO {
    private int id;
    private List<ProductDTO> products;
    private int totalPrice;

    @Builder
    public OrderRespFindAllDTO(int id, List<ProductDTO> products, int totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
