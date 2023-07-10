package com.example.kakaoshop.order.response;

import com.example.kakaoshop.product.response.ProductOptionDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SaveProductsDTO {
    private int id;
    private List<Products> products;
    private long totalPrice;

    @Builder
    public SaveProductsDTO(int id, List<Products> products, long totalPrice){
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
