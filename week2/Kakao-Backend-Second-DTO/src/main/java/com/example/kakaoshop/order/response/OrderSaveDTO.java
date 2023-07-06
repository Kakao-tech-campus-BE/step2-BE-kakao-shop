package com.example.kakaoshop.order.response;

import com.example.kakaoshop.cart.response.ProductDTO;

import java.util.List;

public class OrderSaveDTO {
    private int id;
    private List<ProductDTO> products;
    private int totalPrice;
}
