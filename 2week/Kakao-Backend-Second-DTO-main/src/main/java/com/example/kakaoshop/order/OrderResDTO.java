package com.example.kakaoshop.order;

import com.example.kakaoshop.cart.response.ProductDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResDTO {
    private boolean success;
    private List<ProductDTO> products;
    private int totalPrice;
}
