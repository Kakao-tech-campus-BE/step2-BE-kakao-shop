package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProductDTO {

    private String productName;
    private List<ProductOptionDTO> cartItems;

    @Builder
    public ProductDTO(String productName, List<ProductOptionDTO> cartItems) {

        this.productName = productName;
        this.cartItems = cartItems;
    }
}
@Getter @Setter
public class OrderRespFindAllDTO {
    private int id;
    private ProductDTO products;
    private int totalPrice;

    @Builder()
    public OrderRespFindAllDTO(int id, ProductDTO products, int totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}