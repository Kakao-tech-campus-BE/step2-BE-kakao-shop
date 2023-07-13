package com.example.kakaoshop.cart.domain.model;

import com.example.kakaoshop.product.domain.model.ProductOption;
import com.example.kakaoshop.user.User;
import lombok.*;

import java.util.Map;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Cart {
    private int id;
    private User user;
    private ProductOption productOption;
    private int quantity;
}
